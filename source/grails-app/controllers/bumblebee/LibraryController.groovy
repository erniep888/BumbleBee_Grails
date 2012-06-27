package bumblebee

import bumblebee.viewmodel.ArtifactUploadViewModel

class LibraryController {
    def artifactService

    static defaultAction = "edit"

    def edit() {
    }

    def deleteAttachment(){
        Project project = Project.findById(1)
        Artifact attachment = Artifact.findById(params.id)

        project.libraryAttachments.remove(attachment)
        artifactService.deleteArtifact( attachment.id )
        redirect(action:"edit")
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
        Project project = Project.findById(1)
        project.addToLibraryAttachments(artifact)
        project.save(flush: true)
        redirect(action: "edit")
    }

}
