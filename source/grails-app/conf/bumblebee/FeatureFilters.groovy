package bumblebee

class FeatureFilters {
    def mantisIntegrationService
    def filters = {
        featurePhaseBug(controller: 'featurePhaseBug', action: 'edit|editBug'){
            before = {

            }
            after = { Map model ->
                SortedSet<MantisBugInformation> mantisBugs = new TreeSet<MantisBugInformation>()
                if (model?.featurePhaseInstance){
                    for (def featurePhaseBug in model.featurePhaseInstance.bugs) {
                        mantisBugs.add(mantisIntegrationService.findMantisBugInformationById(featurePhaseBug.bugSystemId))
                    }
                }
                model.mantisBugs = mantisBugs.sort()
            }
            afterView = { Exception e ->

            }
        }
        allFeaturePhase(controller: 'featurePhase*', action: '*', actionExclude: 'viewTest|viewAttachment') {
            before = {
                if (!params || !params.featureId){
                    flash.message = "${grailsApplication.getMainContext().getMessage("feature.label", null, Locale.ENGLISH)} id must be supplied."
                    redirect(action:"list", controller: "feature")
                    return
                }

                def feature = Feature.findById(params.featureId)
                if (!feature) {
                    flash.message = "${grailsApplication.getMainContext().getMessage("feature.label", null, Locale.ENGLISH)} not found with id: ${params.featureId}"
                    redirect(action:"list", controller: "feature")
                    return
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
        feature(controller: 'feature', action: 'edit|delete') {
            before = {
                if (!params || !params.id){
                    flash.message = "${grailsApplication.getMainContext().getMessage("feature.label", null, Locale.ENGLISH)} id must be supplied."
                    redirect(action:"list", controller: "feature")
                    return
                }

                def feature = Feature.findById(params.id)
                if (!feature) {
                    flash.message = "${grailsApplication.getMainContext().getMessage("feature.label", null, Locale.ENGLISH)} not found with id: ${params.id}"
                    redirect(action:"list", controller: "feature")
                    return
                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
