package bumblebee

class FeatureController {
    def mantisIntegrationService

    def index() {
        redirect(action: 'list')
    }

    def list() {
        [featureInstanceList: Feature.list(), featureInstanceTotal: 0]
    }

    def create() {
        [featureInstance: new Feature()]
    }
}
