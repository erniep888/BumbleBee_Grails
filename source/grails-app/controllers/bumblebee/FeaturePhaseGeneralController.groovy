package bumblebee

class FeaturePhaseGeneralController extends FeaturePhaseController {

    def save() {
        def justPostedFeaturePhase = new FeaturePhase(params)
        justPostedFeaturePhase.status = FeaturePhaseStatus.findByStatus(params.status)
        def feature = Feature.findById(params.featureId)

        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))

        def completedStatus = FeaturePhaseStatus.findByStatus('completed')
        if (justPostedFeaturePhase.status == completedStatus && selectedFeaturePhase.status != completedStatus){
            selectedFeaturePhase.status = completedStatus
            selectedFeaturePhase.executionDate = new Date()
        } else if (justPostedFeaturePhase.status != selectedFeaturePhase.status){
            selectedFeaturePhase.status = justPostedFeaturePhase.status
            selectedFeaturePhase.executionDate = null
        }

        selectedFeaturePhase.developer = justPostedFeaturePhase.developer
        selectedFeaturePhase.tester = justPostedFeaturePhase.tester
        selectedFeaturePhase.developmentWorkEffort = justPostedFeaturePhase.developmentWorkEffort
        selectedFeaturePhase.testWorkEffort = justPostedFeaturePhase.testWorkEffort
        selectedFeaturePhase.status = (justPostedFeaturePhase.status) ?
            justPostedFeaturePhase.status : selectedFeaturePhase.status
        selectedFeaturePhase.isOffShore = (justPostedFeaturePhase.isOffShore) ?
            justPostedFeaturePhase.isOffShore : false
        selectedFeaturePhase.comments = justPostedFeaturePhase.comments

        selectedFeaturePhase.validate()
        if (selectedFeaturePhase?.hasErrors()){
            if (!selectedFeaturePhase || !selectedFeaturePhase.id)
                render(view: "create", model: [featurePhaseInstance: selectedFeaturePhase], params: params)
            else
                render(view: "edit", model: [featurePhaseInstance: selectedFeaturePhase], params: params)
        } else {
            selectedFeaturePhase.save(flush: true)
            redirect(controller: "FeaturePhaseGeneral", action:"edit", params: [featureId: params.featureId, id: params.id])
        }
    }
}
