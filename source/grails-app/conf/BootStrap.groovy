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
import bumblebee.ActiveDirectoryService
import bumblebee.ActiveDirectoryUserInformation
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {

    def init = { servletContext ->
        createActiveDirectorySettings()
        createBugSystemSettings()
        createPhases()
        createProject()
        createFeatureStatuses()
        createFeaturePhaseCaseStatuses()
        createVendors()
        //createAdministrators(servletContext)

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
					hostname: 'ad', port: '389', bindDn: 'test@scrumtime.com',
					bindPassword: 'scrumtime1',
					keystorePath: /\\share1\jssecacerts/,
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
			Administrator administrator1 = new Administrator(username: 'pascherk', fullName: userInfo.givenName + ' ' + userInfo.lastName)
			administrator1.save(flush: true)
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
			def featureStatus10 = new FeaturePhaseStatus(priority: 10, status: "[a] not started", shortReference: 'a')
			featureStatus10.save()
			def featureStatus20 = new FeaturePhaseStatus(priority: 20, status: "[b] in development", shortReference: 'b')
			featureStatus20.save()			
			def featureStatus40 = new FeaturePhaseStatus(priority: 40, status: "[c] in rework", shortReference: 'c')
			featureStatus40.save()
			def featureStatus50 = new FeaturePhaseStatus(priority: 50, status: "[d] ready for test", shortReference: 'd')
			featureStatus50.save()
			def featureStatus60 = new FeaturePhaseStatus(priority: 60, status: "[e] in test", shortReference: 'e')
			featureStatus60.save()
			def featureStatus70 = new FeaturePhaseStatus(priority: 70, status: "[f] failed", shortReference: 'f')			
			featureStatus70.save()
			def featureStatus75 = new FeaturePhaseStatus(priority: 75, status: "[g] ready for production", shortReference: 'g')
			featureStatus75.save()
			def featureStatus80 = new FeaturePhaseStatus(priority: 80, status: "[h] completed", shortReference: 'h')
			featureStatus80.save(flush: true)
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
