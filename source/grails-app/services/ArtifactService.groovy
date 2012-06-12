package bumblebee

import javax.annotation.PostConstruct

class ArtifactService {

    HashMap<String, String> subfolderDictionary
    String rootFolder
    def grailsApplication

    @PostConstruct
    def initialize() {
        def applicationAcronym = grailsApplication.config.grails.userfriendly.applicationAcronym
        rootFolder = File.separator + applicationAcronym + File.separator + "Artifacts"
        subfolderDictionary = new HashMap<String, String>()
        def folderNames = new Vector<String>()
        folderNames.add("tests")
        folderNames.add("attachments")
        folderNames.add("library")
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

    def save(Artifact artifact, String targetFolderName,byte[] bytes, def artifactContainerModel){
        artifact.serverFilePath = subfolderDictionary[targetFolderName]
        artifact.serverFileName = UUID.randomUUID().toString();
        def fullPathAndFileName = artifact.serverFilePath + File.separator + artifact.serverFileName
        def fileOutputStream = new FileOutputStream(fullPathAndFileName)
        fileOutputStream.write(bytes)
        fileOutputStream.close()
        artifactContainerModel.addToArtifacts(artifact)
        artifact.save()
        artifactContainerModel.save()
    }

    def load(Artifact artifact) {
        def fullPathAndFileName = artifact.serverFilePath + File.separator + artifact.serverFileName
        return new FileInputStream(fullPathAndFileName)
    }
}
