package bumblebee

class FeaturePhaseGeneralController {

    static defaultAction = "edit"

    def edit(long featureId, long id) {
        def feature = Feature.findById(featureId)
        [featureInstance: feature]
    }
}
