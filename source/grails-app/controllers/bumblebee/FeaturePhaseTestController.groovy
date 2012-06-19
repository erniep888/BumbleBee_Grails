package bumblebee

import bumblebee.viewmodel.ArtifactUploadViewModel

class FeaturePhaseTestController {
    def artifactService

    static defaultAction = "edit"

    def edit(long featureId, long id) {
        def feature = Feature.findById(featureId)
        if (!feature.featurePhases || feature.featurePhases.size() == 0){
            def phase = Phase.findById(id)
            def featurePhaseGeneral = new FeaturePhase(feature: feature, phase: phase, status: "not started")
            featurePhaseGeneral.save(flush: true)
        }
        def selectedFeaturePhase = feature.featurePhases.find {it.phase.id == id}
        [featureInstance: feature, featurePhaseInstance: selectedFeaturePhase]
    }

    def deleteTest(){
        long testId = params.id
        long featurePhaseId = params.featurePhaseId
    }

    def save(){

    }

    def viewTest(){
        long testId = params.id
    }

    def uploadForm(){
        def id = params.id
        [id:id]
    }

    def upload(ArtifactUploadViewModel artifactUploadViewModel) {
        if (!artifactUploadViewModel){
            flash.message = message(code: 'artifact.emptyFileUpload.message')
            redirect(view: 'edit')
            return
        }

        // create the Artifact
        Artifact artifact = new Artifact(
                fileName: artifactUploadViewModel.contents.getOriginalFilename(),
                contentType: artifactUploadViewModel.contents.contentType,
                size: artifactUploadViewModel.contents.bytes.length)

        artifactService.saveObjectTestArtifact(artifact, artifactUploadViewModel.contents.bytes)
        redirect(action: "edit", params: params)
    }
}
