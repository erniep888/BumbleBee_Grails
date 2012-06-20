package bumblebee

import bumblebee.viewmodel.ArtifactUploadViewModel
import org.aspectj.weaver.ast.Test

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
        def selectedFeaturePhase = feature.featurePhases.find {it.phase.id == new Long(params.id)}
        [featureInstance: feature, featurePhaseInstance: selectedFeaturePhase]
    }

    def deleteTest(){
        def feature = Feature.findById(params.featureId)
        def selectedFeaturePhase = feature.featurePhases.find {it.phase.id == new Long(params.id)}
        Artifact test = Artifact.findById(params.artifactId)

        selectedFeaturePhase.tests.remove(test)
        artifactService.deleteArtifact( test.id )
        redirect(action:"edit", params: [id: params.id, featureId: params.featureId])
    }

    def viewTest(){
        def testArtifact = Artifact.findById(params.id)
        response.contentType = testArtifact.contentType
        response.addHeader("Content-Disposition", "attachment; fileName=${testArtifact.fileName}")
        response.addHeader("Content-Length", String.valueOf(testArtifact.size))
        File file = new File(testArtifact.serverFilePath + File.separator + testArtifact.serverFileName)
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

        artifactService.saveObjectTestArtifact(artifact, artifactUploadViewModel.contents.bytes)
        Feature feature = Feature.findById(params.featureId)
        FeaturePhase selectedFeaturePhase = feature.featurePhases.find {it.phase.id == new Long(params.id)}
        selectedFeaturePhase.tests.add(artifact)
        selectedFeaturePhase.save(flush: true)
        redirect(action: "edit", params: params)
    }
}
