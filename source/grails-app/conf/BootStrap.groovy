import bumblebee.Phase
import bumblebee.Feature
import bumblebee.FeaturePhase

class BootStrap {

    def init = { servletContext ->
        createPhases()
        createFeature()
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

    private void createFeature(){
        if (Feature.count() == 0){
            Feature feature = new Feature(category: "Inventory",
                    description: "Inventory master", name: "R57-200", isDeleted: false,
                    featurePhases: new TreeSet<FeaturePhase>())
            feature.save(flush: true)
            def phase1 = Phase.findById(1)
            def featurePhaseGeneral = new FeaturePhase(feature: feature, phase: phase1, status: "Not Started",
                developer: "John Smith")
            featurePhaseGeneral.save(flush: true)

            feature.featurePhases.add(featurePhaseGeneral)
            feature.save(flush: true)
        }
    }

    private Feature addGeneralFeaturePhase1(Feature feature){
        def phase1 = Phase.findById(1)
        def featurePhaseGeneral = new FeaturePhase(feature: feature,
            comments: "No comments", developer: "John Doe", developmentWorkEffort: 1.0,
            phase: phase1, tester: "Sally Smith", testWorkEffort: 2.0 )
        feature.featurePhases.add(featurePhaseGeneral)
    }
}
