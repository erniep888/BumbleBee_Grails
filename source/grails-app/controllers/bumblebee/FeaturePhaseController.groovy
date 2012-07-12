package bumblebee

class FeaturePhaseController {
    def artifactService

    static defaultAction = "edit"

    def edit(long featureId, long id) {
        def feature = Feature.findById(featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, id)
        [featureInstance: feature, featurePhaseInstance: selectedFeaturePhase]
    }

    protected FeaturePhase getOrSetSelectedFeaturePhase(Feature feature, long phaseId){
        def phase = Phase.findById(phaseId)
        def selectedFeaturePhase = FeaturePhase.findOrCreateWhere(phase: phase, feature: feature)
        if (!selectedFeaturePhase.id){
            selectedFeaturePhase.status = FeaturePhaseStatus.findByStatus('[a] not started')
            selectedFeaturePhase.save(flush: true)
            feature.addToFeaturePhases(selectedFeaturePhase)
            feature.save()
        }
        return selectedFeaturePhase
    }
}
