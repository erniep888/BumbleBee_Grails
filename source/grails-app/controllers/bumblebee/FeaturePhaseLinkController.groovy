package bumblebee

class FeaturePhaseLinkController {

    static defaultAction = "edit"

    def edit(long featureId, long id) {
        def feature = Feature.findById(featureId)
        [featureInstance: feature]
    }
}
