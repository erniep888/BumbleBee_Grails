package bumblebee

class FeaturePhaseCaseController extends FeaturePhaseController{

    def editCase(long featureId, long caseId, long id) {
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        def selectedCase = FeaturePhaseCase.findById(caseId)

        render(view: "edit",
                model: [featureInstance: feature,
                        featurePhaseInstance: selectedFeaturePhase, caseInstance: selectedCase])
    }

    def deleteCase(long featureId, long caseId, long id) {
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        def featurePhaseCase = FeaturePhaseCase.findById(caseId)
        selectedFeaturePhase.cases.remove(featurePhaseCase)
        featurePhaseCase.delete(flush: true)
        redirect(action: "edit", params: params)
    }

    def save(){
        def postedFeaturePhaseCase = new FeaturePhaseCase(params)
        postedFeaturePhaseCase.status = FeaturePhaseCaseStatus.findByStatus(params.caseInstance.status)
        postedFeaturePhaseCase.vendor = Vendor.findByName(params.caseInstance.vendor)

        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        postedFeaturePhaseCase.featurePhase = selectedFeaturePhase
        postedFeaturePhaseCase.validate()
        if (postedFeaturePhaseCase.hasErrors()){
            flash.message = postedFeaturePhaseCase.errors.getAllErrors().first().toString()
            redirect(action: "edit", params: params)
        } else {
            FeaturePhaseCase existingCase = null
            if (params.caseId)
                existingCase = FeaturePhaseCase.findById(params.caseId)
            if (existingCase){
                existingCase.caseIdentifier = postedFeaturePhaseCase.caseIdentifier
                existingCase.status = postedFeaturePhaseCase.status
                existingCase.href = postedFeaturePhaseCase.href
                existingCase.vendor = postedFeaturePhaseCase.vendor
                existingCase.description = postedFeaturePhaseCase.description
                existingCase.save(flush: true)
            } else {
                postedFeaturePhaseCase.save(flush: true)
                if (!selectedFeaturePhase.cases)
                    selectedFeaturePhase.cases = new TreeSet<FeaturePhaseCase>()
                selectedFeaturePhase.cases.add(postedFeaturePhaseCase)
                selectedFeaturePhase.save(flush: true)
            }
            redirect(action: "edit", params: params)
        }
    }
}
