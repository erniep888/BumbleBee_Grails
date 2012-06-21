package bumblebee

class FeaturePhaseLinkController extends FeaturePhaseController{

    def editLink(long featureId, long linkId, long id) {
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        def selectedLink = Link.findById(linkId)

        render(view: "edit",
                model: [featureInstance: feature,
                        featurePhaseInstance: selectedFeaturePhase, linkInstance: selectedLink])
    }

    def deleteLink(long featureId, long linkId, long id) {
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        def link = Link.findById(linkId)
        selectedFeaturePhase.links.remove(link)
        link.delete(flush: true)
        redirect(action: "edit", params: params)
    }

    def save(Link link){
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = getOrSetSelectedFeaturePhase(feature, new Long(params.id))
        link.featurePhase = selectedFeaturePhase
        link.validate()
        if (link.hasErrors()){
            flash.message = link.errors.getAllErrors().first().toString()
            redirect(action: "edit", params: params)
        } else {
            Link existingLink = null
            if (params.linkId)
                existingLink = Link.findById(params.linkId)
            if (existingLink){
                existingLink.name = link.name
                existingLink.href = link.href
                existingLink.inNewWindow = link.inNewWindow
                existingLink.save(flush: true)
            } else {
                link.save(flush: true)
                if (!selectedFeaturePhase.links)
                    selectedFeaturePhase.links = new TreeSet<Link>()
                selectedFeaturePhase.links.add(link)
                selectedFeaturePhase.save(flush: true)
            }
            redirect(action: "edit", params: params)
        }
    }
}
