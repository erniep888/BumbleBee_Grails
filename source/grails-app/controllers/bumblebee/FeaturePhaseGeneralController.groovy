package bumblebee

class FeaturePhaseGeneralController extends FeaturePhaseController {

    def save() {
        def justPostedFeaturePhase = new FeaturePhase(params)
        def feature = Feature.findById(params.featureId)
        def phase = Phase.findById(params.id)

        def existingFeaturePhase = FeaturePhase.findOrCreateWhere(phase: phase, feature: feature)
        if (!existingFeaturePhase.id)
            existingFeaturePhase.status = 'not started'
        if (justPostedFeaturePhase.status == 'completed' && existingFeaturePhase.status != 'completed'){
            existingFeaturePhase.status = 'completed'
            existingFeaturePhase.executionDate = new Date()
        } else if (justPostedFeaturePhase.status != existingFeaturePhase.status){
            existingFeaturePhase.status = justPostedFeaturePhase.status
            existingFeaturePhase.executionDate = null
        }

        if (existingFeaturePhase){
            existingFeaturePhase.developer = justPostedFeaturePhase.developer
            existingFeaturePhase.tester = justPostedFeaturePhase.tester
            existingFeaturePhase.developmentWorkEffort = justPostedFeaturePhase.developmentWorkEffort
            existingFeaturePhase.testWorkEffort = justPostedFeaturePhase.testWorkEffort
            existingFeaturePhase.status = (justPostedFeaturePhase.status) ?
                justPostedFeaturePhase.status : existingFeaturePhase.status
            existingFeaturePhase.comments = justPostedFeaturePhase.comments
        }

        existingFeaturePhase.validate()
        if (existingFeaturePhase?.hasErrors()){
            if (!existingFeaturePhase || !existingFeaturePhase.id)
                render(view: "create", model: [featurePhaseInstance: existingFeaturePhase], params: params)
            else
                render(view: "edit", model: [featurePhaseInstance: existingFeaturePhase], params: params)
        } else {
            feature.featurePhases.add(existingFeaturePhase)
            feature.save(flush: true)
            existingFeaturePhase.save(flush: true)
            redirect(controller: "FeaturePhaseGeneral", action:"edit", params: [featureId: params.featureId, id: params.id])
        }
    }
}
