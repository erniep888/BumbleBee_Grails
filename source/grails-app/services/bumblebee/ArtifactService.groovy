package bumblebee

import javax.annotation.PostConstruct

class ArtifactService {

    HashMap<String, String> subfolderDictionary
    def grailsApplication
    String library
    String tests
    String attachments
    String rootFolder

    @PostConstruct
    def initialize() {
        library = "library"
        tests = grailsApplication.getMainContext().getMessage("feature.label").toLowerCase() + "_tests"
        attachments = grailsApplication.getMainContext().getMessage("feature.label").toLowerCase() + "_attachments"
        rootFolder = File.separator + grailsApplication.getMainContext().getMessage("app.userfriendly.applicationAcronym") + File.separator + "artifacts"

        subfolderDictionary = new HashMap<String, String>()
        def folderNames = new Vector<String>()
        folderNames.add(tests)
        folderNames.add(attachments)
        folderNames.add(library)
        for(def folderName in folderNames){
            subfolderDictionary.put(folderName, rootFolder + File.separator + folderName)
        }

        def rootFolderDir = new File(rootFolder)
        if (!rootFolderDir.exists()) {  // the root folder does not exist
            rootFolderDir.mkdir()
            for(def directory in subfolderDictionary.values()){
                def subFolderDir = new File(directory)
                if (!subFolderDir.exists())  // the sub folder does not exist
                    subFolderDir.mkdir()
            }
        }
    }

    private Artifact saveArtifact(Artifact artifact, byte[] bytes){
        artifact.validate()
        if (!artifact.hasErrors()){
            def existingArtifact = Artifact.findById(artifact?.id)
            if (existingArtifact) {
                existingArtifact.lock()
                def existingFullPathAndFileName =
                    existingArtifact.serverFilePath + File.separator + existingArtifact.serverFileName
                def existingFile = new File(existingFullPathAndFileName)
                if (existingFile.exists()){
                    existingFile.delete()
                }
                existingArtifact = handleArtifactFile(existingArtifact, bytes)
                existingArtifact.fileName = artifact.fileName
                existingArtifact.description = artifact.description
                existingArtifact.save(flush: true)
            }  else {
                artifact = handleArtifactFile(artifact, bytes)
                artifact.save(flush: true)
            }
        }
        return artifact;
    }

    private Artifact handleArtifactFile(Artifact artifact, byte[] bytes){
        artifact.serverFilePath = subfolderDictionary[library]
        artifact.serverFileName = UUID.randomUUID().toString()
        artifact.size = bytes.length
        saveArtifactFile(artifact.serverFilePath, artifact.serverFileName, bytes)
        return artifact
    }

    private void saveArtifactFile(String serverFilePath, String serverFileName, byte[] bytes){
        def fullPathAndFileName = serverFilePath + File.separator + serverFileName
        def fileOutputStream = new FileOutputStream(fullPathAndFileName)
        fileOutputStream.write(bytes)
        fileOutputStream.close()
    }


    def load(Artifact artifact) {
        def fullPathAndFileName = artifact.serverFilePath + File.separator + artifact.serverFileName
        return new FileInputStream(fullPathAndFileName)
    }
}
