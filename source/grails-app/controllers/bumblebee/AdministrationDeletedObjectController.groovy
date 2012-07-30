package bumblebee

class AdministrationDeletedObjectController {
    def cacheService
    def artifactService

    static defaultAction = "list"

    def list() {
        [features: Feature.findAllByIsDeleted(true)]
    }

    def delete(long id){
        def feature = Feature.findById(id)
        def phases = FeaturePhase.findByFeature(feature)
        for(FeaturePhase phase in phases){
            for(Artifact artifact in phase.attachments){
                artifactService.deleteArtifact(artifact)
            }
            for(Artifact artifact in phase.tests){
                artifactService.deleteArtifact(artifact)
            }
            for(FeaturePhaseBug bug in phase.bugs){
                bug.delete()
            }
            for(FeaturePhaseCase phaseCase in phase.cases){
                phaseCase.delete()
            }
            phase.delete()
        }
        feature.delete()
        redirect(action: "list")
    }

    def recover(long id){
        def feature = Feature.findById(id)
        feature.isDeleted = false
        feature.save(flush: true)
        cacheService.invalidate(FeatureController.FEATURELIST_JSON_KEY)
        redirect(action: "list")
    }

}
