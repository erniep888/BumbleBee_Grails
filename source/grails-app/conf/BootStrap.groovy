import bumblebee.Phase
import bumblebee.Feature
import bumblebee.FeaturePhase
import bumblebee.Project
import bumblebee.Link
import bumblebee.FeaturePhaseStatus
import bumblebee.FeaturePhaseCaseStatus
import bumblebee.Vendor
import bumblebee.BugSystemSettings
import bumblebee.ActiveDirectorySettings
import bumblebee.Administrator
import bumblebee.Contributor
import bumblebee.ActiveDirectoryService
import bumblebee.ActiveDirectoryUserInformation
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils
import bumblebee.Contributor

class BootStrap {

    def init = { servletContext ->
        createActiveDirectorySettings()
        createBugSystemSettings()
        createPhases()
        createProject()
        createFeatureStatuses()
        createFeaturePhaseCaseStatuses()
        createVendors()
        //createContributors(servletContext)
        //createAdministrators(servletContext)
        //loadFeaturesFromCSV(servletContext)
        //createManyFeatures()
    }
    def destroy = {
    }

    private void createBugSystemSettings(){
        if (BugSystemSettings.count() == 0) {
            BugSystemSettings bugSystemSettings = new BugSystemSettings(
                    systemName: "Mantis", bugAccessUrl: "http://mantis/view.php?id=")
            bugSystemSettings.save(flush: true)
        }
    }

    private void createActiveDirectorySettings(){
        if (ActiveDirectorySettings.count() == 0) {
            ActiveDirectorySettings activeDirectorySettings = new ActiveDirectorySettings(
                    hostname: 'ldap', port: '389', bindDn: 'ldap@scrumtime.com',
                    bindPassword: 'test1',
                    keystorePath: /c:/,
                    ldapSearchString: "DC=scrumtime,DC=com"

            )
            activeDirectorySettings.save(flush: true)
        }
    }

    private void createAdministrators(def servletContext){
        if (Administrator.count() == 0){
            WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(servletContext)
            ActiveDirectoryService activeDirectoryService = appCtx.getBean('activeDirectoryService')
            ActiveDirectoryUserInformation userInfo = activeDirectoryService.retrieveUserInformation('pascherk')
            Administrator administrator = new Administrator(username: 'pascherk',
                    fullName: userInfo.givenName + ' ' + userInfo.lastName, emailAddress: userInfo.emailAddress)
            administrator.save(flush: true)
        }
    }

    private void createContributors(def servletContext){
        if (Contributor.count() == 0){
            WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(servletContext)
            ActiveDirectoryService activeDirectoryService = appCtx.getBean('activeDirectoryService')
            ActiveDirectoryUserInformation userInfo = activeDirectoryService.retrieveUserInformation('pascherk')
            Contributor contributor1 = new Contributor(username: 'pascherk',
                    fullName: userInfo.givenName + ' ' + userInfo.lastName, emailAddress: userInfo.emailAddress)
            contributor1.save(flush: true)
        }
    }

    private void createPhases(){
        if (Phase.count() == 0){
            Phase phase1 = new Phase(name: "L1/L2 Test", priority: 1)
            phase1.save()
            Phase phase2 = new Phase(name: "SME/L3 Unit Test", priority: 2)
            phase2.save()
            Phase phase3 = new Phase(name: "Integration 1", priority: 3)
            phase3.save()
            Phase phase4 = new Phase(name: "Integration 2", priority: 4)
            phase4.save(flush: true)
        }
    }

    private void createProject(){
        if (Project.count() == 0){
            Project project = new Project(name: "JDE 9.1 Upgrade", description: "JDE 9.1 Upgrade")
            project.save(flush: true)
        }
    }

    private void createFeatureStatuses(){
        if (FeaturePhaseStatus.count() == 0){
            def featureStatus10 = new FeaturePhaseStatus(priority: 10, shortReference: "a", status: "[a] not started")
            featureStatus10.save()
            def featureStatus20 = new FeaturePhaseStatus(priority: 20, shortReference: "b", status: "[b] in development")
            featureStatus20.save()
            def featureStatus30 = new FeaturePhaseStatus(priority: 30, shortReference: "c", status: "[c] ready for production")
            featureStatus30.save()
            def featureStatus40 = new FeaturePhaseStatus(priority: 40, shortReference: "d", status: "[d] in rework")
            featureStatus40.save()
            def featureStatus50 = new FeaturePhaseStatus(priority: 50, shortReference: "e", status: "[e] ready for test")
            featureStatus50.save()
            def featureStatus60 = new FeaturePhaseStatus(priority: 60, shortReference: "f", status: "[f] in test")
            featureStatus60.save()
            def featureStatus70 = new FeaturePhaseStatus(priority: 70, shortReference: "g", status: "[g] failed")
            featureStatus70.save()
            def featureStatus80 = new FeaturePhaseStatus(priority: 80, shortReference: "h", status: "[h] completed")
            featureStatus80.save(flush: true)
        }
    }

    private void createManyFeatures(){
        if (Feature.count() == 0){
            def phase1 = Phase.findById(1)
            def project = Project.findById(1)
            for(int i = 0; i < 10; i++){
                Feature feature = new Feature(project: project, category: "Inventory",
                        description: "Inventory master " + i, name: "R57204" + i, isDeleted: false,
                        featurePhases: new TreeSet<FeaturePhase>(), module: "Inventory")
                feature.save(flush: true)
                def featurePhaseGeneral = new FeaturePhase(feature: feature, phase: phase1, status: FeaturePhaseStatus.findByPriority(10),
                        developer: Contributor.findById(1) ,links: new TreeSet<Link>())
                featurePhaseGeneral.save(flush: true)

                def link = new Link(name: "MyPortal", href: "http://myportal", inNewWindow: true, featurePhase: featurePhaseGeneral)
                link.save()
                featurePhaseGeneral.links.add(link)
                featurePhaseGeneral.save(flush: true)

                feature.featurePhases.add(featurePhaseGeneral)
                feature.save(flush: true)
            }
        }
    }

    private void loadFeaturesFromCSV(def servletContext){
        WebApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(servletContext)
        ActiveDirectoryService activeDirectoryService = appCtx.getBean('activeDirectoryService')


        def phase1 = Phase.findById(1)
        def project = Project.findById(1)
        int x = 1
        try{
            new File("d:/Grails-Work/Sims_Upload.csv").splitEachLine(",") {fields ->
                Feature feature = new Feature(project: project, category: fields[2],
                        description: fields[1], name: fields[0], isDeleted: false,
                        featurePhases: new TreeSet<FeaturePhase>(), module: fields[3])
                feature.save(flush: true)
                def comments = fields[4]
                def offshore = (fields[5] == 'S')

                def developer =(Contributor.findByFullName(fields[6]))?:null
                if (developer == null){
                    def names = fields[6].split()
                    List<ActiveDirectoryUserInformation> userInfos = activeDirectoryService.findUsers('',names[0], names[1] )
                    if (userInfos.size() == 1){
                        def contributor = new Contributor(username: userInfos.first().userPrincipalName,
                                fullName: fields[6], emailAddress: userInfos.first().emailAddress)
                        contributor.save(flush: true)
                        developer = contributor
                    }
                }
                def sme = (Contributor.findByFullName(fields[7]))?:null
                if (sme == null){
                    def names = fields[7].split()
                    List<ActiveDirectoryUserInformation> userInfos = activeDirectoryService.findUsers('',names[0], names[1] )
                    if (userInfos.size() == 1){
                        def contributor = new Contributor(username: userInfos.first().userPrincipalName,
                                fullName: fields[7], emailAddress: userInfos.first().emailAddress)
                        contributor.save(flush: true)
                        sme = contributor
                    }
                }
                def devEffort = fields[8]
                def testEffort = fields[9]
                def featurePhaseGeneral = new FeaturePhase(feature: feature, phase: phase1, status: FeaturePhaseStatus.findByPriority(10),
                        developer: developer, tester: sme, devEffort: devEffort, testEffort: testEffort, isOffShore: offshore, comments: comments)
                featurePhaseGeneral.validate()
                featurePhaseGeneral.save(flush: true)

                feature.featurePhases.add(featurePhaseGeneral)
                feature.save(flush: true)
//                if (x.equals(270))
//                    target = x
                x++
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
    }


    private void createFeaturePhaseCaseStatuses(){
        if (FeaturePhaseCaseStatus.count() == 0){
            def status10 = new FeaturePhaseCaseStatus(status: "unknown", priority: 10)
            status10.save()
            def status20 = new FeaturePhaseCaseStatus(status: "waiting on vendor", priority: 20)
            status20.save()
            def status30 = new FeaturePhaseCaseStatus(status: "vendor needs more info", priority: 30)
            status30.save()
            def status40 = new FeaturePhaseCaseStatus(status: "will not fix", priority: 40)
            status40.save()
            def status50 = new FeaturePhaseCaseStatus(status: "fix in work", priority: 50)
            status50.save()
            def status60 = new FeaturePhaseCaseStatus(status: "corrected: not deployed", priority: 60)
            status60.save()
            def status70 = new FeaturePhaseCaseStatus(status: "corrected: deployed", priority: 70)
            status70.save(flush: true)
        }
    }

    private void createVendors(){
        if (Vendor.count() == 0){
            def unknown = new Vendor(priority: 5, name: "unknown")
            unknown.save()
            def oracle = new Vendor(priority: 10, name: "Oracle")
            oracle.save()
            def seltec = new Vendor(priority: 20, name: "Seltec")
            seltec.save(flush: true)
        }
    }
}
