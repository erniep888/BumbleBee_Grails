package bumblebee

import org.springframework.context.ApplicationContextAware
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.context.ApplicationContext
import org.springframework.beans.BeansException

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.dao.EmptyResultDataAccessException

class MantisIntegrationService implements ApplicationContextAware {
    GrailsApplication grailsApplication
    ApplicationContext appCtx

    def void setApplicationContext(ApplicationContext arg0) throws BeansException {
        appCtx = arg0;
    }

    boolean transactional = true

    def findMantisBugInformationById(def id) throws Exception {
        def map
        def jt = new JdbcTemplate(appCtx.getBean("dataSourceMantis"))
        def sqlString = "select id, priority, severity, status, summary from mantis_bug_table where id = " + id
        try {
            map = jt.queryForMap(sqlString)
        } catch (EmptyResultDataAccessException erdae){
            throw new Exception("There is no bug in Mantis with that id.")
        } catch (Exception ex) {
            throw new Exception("Unable to connect to Mantis.")
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
}
