import org.springframework.context.ApplicationContextAware
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.context.ApplicationContext
import org.springframework.beans.BeansException
import bumblebee.MantisBugInformation
import bumblebee.AuditActivity
import org.springframework.jdbc.core.JdbcTemplate

class MantisIntegrationService implements ApplicationContextAware {
    GrailsApplication grailsApplication
    ApplicationContext appCtx

    def void setApplicationContext(ApplicationContext arg0) throws BeansException {
        appCtx = arg0;
    }

    boolean transactional = true

    MantisBugInformation saveBugInformation(def bugInformation, def bugId) {
        def existingBugInformation = MantisBugInformation.findByBugId(bugInformation?.bugId)
        if (existingBugInformation) {
            existingBugInformation.lock()
            existingBugInformation.summary = bugInformation.summary
            existingBugInformation.category = bugInformation.category
            existingBugInformation.status = bugInformation.status
            existingBugInformation.severity = bugInformation.severity
            existingBugInformation.priority = bugInformation.priority
            existingBugInformation.save(flush:true)
            bugInformation = existingBugInformation
        } else {
            bugInformation.save(flush: true)
        }
        return bugInformation
    }

    def addBugToScenario(def bugId, def testScenario, def username) {
        def bugInformation = findMantisBugById(bugId)
        if (bugInformation?.bugId && testScenario) {
            testScenario.lock()
            bugInformation = saveBugInformation(bugInformation,bugId)
            def mantisBug = new MantisBugInformation(id: bugInformation.id,
                    testScenario: testScenario)
            if (!testScenario.mantisBugs) {
                testScenario.mantisBugs = new TreeSet<MantisBugInformation>()
            }
            testScenario.mantisBugs.add(mantisBug)
            testScenario.save(flush: true)
            AuditActivity auditActivity = new AuditActivity(type: "test scenario",
                    description: testScenario.id + " was saved with new bug (" + mantisBug.id + ")",
                    username: username)
            auditActivity.save(flush: true)
        } else {
            bugInformation = null
        }
        return bugInformation
    }

    def findMantisBugsByScenario(def testScenario) {
        SortedSet mantisBugInfos
        if (testScenario && testScenario?.mantisBugs && testScenario?.mantisBugs?.size() > 0) {
            mantisBugInfos = new TreeSet<MantisBugInformation>()
            for (mantisBug in testScenario.mantisBugs) {
                mantisBugInfos.add(MantisBugInformation.findByBugId(mantisBug.bugId))
            }
        }
        return mantisBugInfos
    }

    def findMantisBugById(def id) {
        def map
        def jt = new JdbcTemplate(appCtx.getBean("dataSourceMantis"))
        def sqlString = "select id, priority, severity, status, summary from mantis_bug_table where id = " + id
        try {
            map = jt.queryForMap(sqlString)
        } catch (Exception ex) {
            println ex
        } finally {
            jt?.getDataSource()?.getConnection()?.close()
        }
        def mantisBugInfo
        if (map)
            mantisBugInfo = MantisBugInformation.createFromMap(map)
        else
            mantisBugInfo = new MantisBugInformation()
        return mantisBugInfo
    }

    public void synchronizeAllBugInformation() {
        // Find all distinct bugs
        def criteria = MantisBug.createCriteria()
        def distinctMantisBugs = criteria.list {
            projections {
                distinct("bugId")
            }
        }

        // for each bug, fetch the latest information from Mantis and save it
        for (mantisBugId in distinctMantisBugs) {
            def existingMantisBugInfo = findMantisBugById(mantisBugId)
            def mantisBugInfo = findMantisBugById(mantisBugId)
            mantisBugInfo = saveBugInformation(mantisBugInfo, mantisBugId)
        }
    }
}
