package bumblebee

class FeaturePhaseBugController extends FeaturePhaseController{
    def mantisIntegrationService

    def editBug(long featureId, long bugId, long id) {
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        def selectedBug = FeaturePhaseBug.findById(bugId)
        def mantisBugInformation = mantisIntegrationService.findMantisBugInformationById(selectedBug.bugSystemId)

        render(view: "edit",
                model: [featureInstance: feature,
                        featurePhaseInstance: selectedFeaturePhase,
                        featurePhaseBugInstance: selectedBug,
                        bugInformationInstance: mantisBugInformation])
    }

    def deleteBug(long featureId, long bugId, long id) {
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        def featurePhaseBug = FeaturePhaseBug.findById(bugId)
        selectedFeaturePhase.cases.remove(featurePhaseBug)
        featurePhaseBug.delete(flush: true)
        redirect(action: "edit", params: params)
    }

    def save(){
        def postedFeaturePhaseBug = new FeaturePhaseBug(params)
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        postedFeaturePhaseBug.featurePhase = selectedFeaturePhase
        try{
            // ensure that the system bug id passed in is valid
            mantisIntegrationService.findMantisBugInformationById(postedFeaturePhaseBug.bugSystemId)
        } catch (Exception ex){
            flash.message = ex.message
            redirect(action: "edit", params: params)
            return
        }
        postedFeaturePhaseBug.validate()
        if (postedFeaturePhaseBug.hasErrors()){
            flash.message = postedFeaturePhaseBug.errors.getAllErrors().first().toString()
            redirect(action: "edit", params: params)
        } else {
            FeaturePhaseBug existingBug = null
            if (params.bugId)
                existingBug = FeaturePhaseBug.findById(params.bugId)
            if (existingBug){     // it does exist
                existingBug.bugSystemId = postedFeaturePhaseBug.bugSystemId
                existingBug.save(flush: true)
            } else {         // it is new
                postedFeaturePhaseBug.save(flush: true)
                if (!selectedFeaturePhase.bugs)
                    selectedFeaturePhase.bugs = new TreeSet<FeaturePhaseBug>()
                selectedFeaturePhase.bugs.add(postedFeaturePhaseBug)
                selectedFeaturePhase.save(flush: true)
            }
            redirect(action: "edit", params: params)
        }
    }

}
