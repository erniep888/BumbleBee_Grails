import bumblebee.Phase
import bumblebee.Feature
import bumblebee.FeaturePhase
import bumblebee.Project
import bumblebee.Link

class BootStrap {

    def init = { servletContext ->
        createPhases()
        createProject()
        createManyFeatures()
    }
    def destroy = {
    }

    private void createPhases(){
        if (Phase.count() == 0){
            Phase phase1 = new Phase(name: "Unit/Dev/L2 Test", priority: 1)
            phase1.save()
            Phase phase2 = new Phase(name: "SME/L3 Unit Test", priority: 2)
            phase2.save()
            Phase phase3 = new Phase(name: "Integration", priority: 3)
            phase3.save()
        }
    }

    private void createProject(){
        if (Project.count() == 0){
            Project project = new Project(name: "JDE 9.1 Upgrade", description: "JDE 9.1 Upgrade")
            project.save(flush: true)
        }
    }

    private void createManyFeatures(){
        if (Feature.count() == 0){
            def phase1 = Phase.findById(1)
            def project = Project.findById(1)
            for(int i = 0; i < 100; i++){
                Feature feature = new Feature(project: project, category: "Inventory",
                        description: "Inventory master " + i, name: "R57-20" + i, isDeleted: false,
                        featurePhases: new TreeSet<FeaturePhase>())
                feature.save(flush: true)
                def featurePhaseGeneral = new FeaturePhase(feature: feature, phase: phase1, status: "not started",
                        developer: "John Smith", links: new TreeSet<Link>())
                featurePhaseGeneral.save(flush: true)

                def link = new Link(name: "Google", href: "http://google.com", inNewWindow: true, featurePhase: featurePhaseGeneral)
                link.save()
                featurePhaseGeneral.links.add(link)
                featurePhaseGeneral.save(flush: true)

                feature.featurePhases.add(featurePhaseGeneral)
                feature.save(flush: true)
            }
        }
    }
}
