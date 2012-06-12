package bumblebee

class FeatureController {

    def index() { }

    def list() {
        [featureInstanceList: Feature.list(), featureInstanceTotal: 0]
    }

    def create() {
        [featureInstance: new Feature()]
    }
}
