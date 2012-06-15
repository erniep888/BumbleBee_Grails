import bumblebee.Phase
import bumblebee.Feature

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
                    description: "Inventory master", name: "R57-200", isDeleted: false)
            feature.save()
        }
    }
}
