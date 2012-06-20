package bumblebee

class FeaturePhaseLinkController extends FeaturePhaseController{

    def editLink(long featureId, long linkId, long id) {
        def result = super.edit(featureId, id)
        def selectedLink = Link.findById(linkId)

        render(view: "edit",
                model: [featureInstance: result.featureInstance,
                        featurePhaseInstance: result.featurePhaseInstance, linkInstance: selectedLink])
    }

    def deleteLink(long featureId, long linkId, long id) {
        def result = super.edit(featureId, id)
        def link = Link.findById(linkId)
        result.featurePhaseInstance.links.remove(link)
        link.delete(flush: true)
        redirect(action: "edit", params: params)
    }

    def save(Link link){
        def result = super.edit((long)params.featureId, (long)params.id)
        link.featurePhase = result.featurePhaseInstance
        link.validate()
        if (link.hasErrors()){
            flash.message = link.errors.getAllErrors().first().toString()
            redirect(action: "edit", params: params)
        } else {
            Link existingLink = null
            if (params.linkId && params.linkId > 0)
                existingLink = Link.findById(params.linkId)
            if (existingLink){
                existingLink.name = link.name
                existingLink.href = link.href
                existingLink.inNewWindow = link.inNewWindow
                existingLink.save(flush: true)
            } else {
                link.save(flush: true)
                if (!result.featurePhaseInstance.links)
                    result.featurePhaseInstance.links = new TreeSet<Link>()
                result.featurePhaseInstance.links.add(link)
                result.featurePhaseInstance.save(flush: true)
            }
            redirect(action: "edit", params: params)
        }
    }
}
