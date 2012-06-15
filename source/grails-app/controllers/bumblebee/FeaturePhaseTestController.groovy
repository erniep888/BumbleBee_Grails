package bumblebee

import bumblebee.viewmodel.ArtifactUploadViewModel

class FeaturePhaseTestController {
    def artifactService

    static defaultAction = "edit"

    def edit(){

    }

    def delete(){

    }

    def save(){

    }

    def view(){

    }

    def uploadForm(){
        def id = params.id
        [id:id]
    }

    def upload(ArtifactUploadViewModel artifactUploadViewModel) {
        if (!artifactUploadViewModel){
            flash.message = message(code: 'artifact.emptyFileUpload.message')
            render(view: 'uploadForm')
            return
        }

        // create the Artifact
        Artifact artifact = new Artifact(
                fileName: artifactUploadViewModel.contents.originalFilename,
                contentType: artifactUploadViewModel.contents.contentType,
                size: artifactUploadViewModel.contents.size)

        artifactService.saveArtifact(artifact, artifactUploadViewModel.contents.bytes)
        response.sendError(200, 'Done')
    }
}
