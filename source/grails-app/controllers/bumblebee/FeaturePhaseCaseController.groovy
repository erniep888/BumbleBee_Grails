package bumblebee

class FeaturePhaseCaseController {

    static defaultAction = "edit"

    def edit(long featureId, long id) {
        def feature = Feature.findById(featureId)
        [featureInstance: feature]
    }
}
