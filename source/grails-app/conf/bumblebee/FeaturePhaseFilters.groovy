package bumblebee

class FeaturePhaseFilters {

    def filters = {
        all(controller: 'featurePhase*', action: '*') {
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
    }
}
