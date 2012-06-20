package bumblebee

class FeaturePhaseController {
    def artifactService

    static defaultAction = "edit"

    def edit(long featureId, long id) {
        def feature = Feature.findById(featureId)
        if (!feature.featurePhases || feature.featurePhases.size() == 0){
            def phase = Phase.findById(id)
            def featurePhaseGeneral = new FeaturePhase(feature: feature, phase: phase, status: "not started")
            featurePhaseGeneral.save(flush: true)
        }
        def selectedFeaturePhase = feature.featurePhases.find {it.phase.id == new Long(params.id)}
        [featureInstance: feature, featurePhaseInstance: selectedFeaturePhase]
    }
}
