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
        tests = grailsApplication.getMainContext().getMessage("feature.label", null, Locale.ENGLISH).toLowerCase() + "_tests"
        attachments = grailsApplication.getMainContext().getMessage("feature.label", null, Locale.ENGLISH).toLowerCase() + "_attachments"
        rootFolder = File.separator + grailsApplication.getMainContext().getMessage("app.userfriendly.applicationAcronym", null, Locale.ENGLISH) + File.separator + "artifacts"

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

    def saveArtifact(Artifact artifact, byte[] bytes){
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
                existingArtifact = storeArtifactFile(existingArtifact, bytes)
                existingArtifact.fileName = artifact.fileName
                existingArtifact.description = artifact.description
                existingArtifact.save(flush: true)
            }  else {
                artifact = storeArtifactFile(artifact, bytes)
                artifact.save(flush: true)
            }
        }
        return artifact
    }

    private Artifact storeArtifactFile(Artifact artifact, byte[] bytes){
        artifact.serverFilePath = subfolderDictionary[library]
        artifact.serverFileName = UUID.randomUUID().toString()
        artifact.size = bytes.length
        def fullPathAndFileName = artifact.serverFilePath + File.separator + artifact.serverFileName
        def fileOutputStream = new FileOutputStream(fullPathAndFileName)
        fileOutputStream.write(bytes)
        fileOutputStream.close()
        return artifact
    }

    def load(Artifact artifact) {
        def fullPathAndFileName = artifact.serverFilePath + File.separator + artifact.serverFileName
        return new FileInputStream(fullPathAndFileName)
    }
}
