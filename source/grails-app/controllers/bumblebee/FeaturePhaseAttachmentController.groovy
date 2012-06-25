package bumblebee

import bumblebee.viewmodel.ArtifactUploadViewModel

class FeaturePhaseAttachmentController extends FeaturePhaseController{

    def deleteAttachment(){
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = feature.featurePhases.find {it.phase.id == new Long(params.id)}
        Artifact attachment = Artifact.findById(params.artifactId)

        selectedFeaturePhase.attachments.remove(attachment)
        artifactService.deleteArtifact( attachment.id )
        redirect(action:"edit", params: [id: params.id, featureId: params.featureId])
    }

    def viewAttachment(){
        def attachmentArtifact = Artifact.findById(params.id)
        response.contentType = attachmentArtifact.contentType
        response.addHeader("Content-Disposition", "attachment; fileName=${attachmentArtifact.fileName}")
        response.addHeader("Content-Length", String.valueOf(attachmentArtifact.size))
        File file = new File(attachmentArtifact.serverFilePath + File.separator + attachmentArtifact.serverFileName)
        response.outputStream << file.getBytes()
    }

    def upload(ArtifactUploadViewModel artifactUploadViewModel) {
        if (!artifactUploadViewModel || artifactUploadViewModel.contents.size == 0){
            flash.message = message(code: 'artifact.emptyFileUpload.message')
            redirect(view: 'edit', params: params)
            return
        }

        // create the Artifact
        Artifact artifact = new Artifact(
                fileName: artifactUploadViewModel.contents.getOriginalFilename(),
                contentType: artifactUploadViewModel.contents.contentType,
                size: artifactUploadViewModel.contents.bytes.length)

        artifactService.saveObjectAttachmentArtifact(artifact, artifactUploadViewModel.contents.bytes)
        Feature feature = Feature.findById(params.featureId)
        FeaturePhase selectedFeaturePhase = feature.featurePhases.find {it.phase.id == new Long(params.id)}
        selectedFeaturePhase.attachments.add(artifact)
        selectedFeaturePhase.save(flush: true)
        redirect(action: "edit", params: params)
    }
}
