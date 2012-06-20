package bumblebee

import javax.annotation.PostConstruct
import org.codehaus.groovy.grails.commons.GrailsApplication

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
        def rootDrive = grailsApplication.getMetadata()['app.root.drive']
        rootFolder = rootDrive + File.separator + grailsApplication.getMainContext().getMessage("app.userfriendly.applicationAcronym", null, Locale.ENGLISH) + File.separator + "artifacts"

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
            rootFolderDir.mkdirs()
            for(def directory in subfolderDictionary.values()){
                def subFolderDir = new File(directory)
                if (!subFolderDir.exists())  // the sub folder does not exist
                    subFolderDir.mkdir()
            }
        }
    }

    def saveLibraryArtifact(Artifact artifact, byte[] bytes )  {
        return saveArtifact(artifact, bytes, library)
    }

    def saveObjectTestArtifact(Artifact artifact, byte[] bytes )  {
        return saveArtifact(artifact, bytes, tests)
    }

    def saveObjectAttachmentArtifact(Artifact artifact, byte[] bytes )  {
        return saveArtifact(artifact, bytes, attachments)
    }

    private Artifact saveArtifact(Artifact artifact, byte[] bytes, String folderName){
        if (!artifact.hasErrors()){
            if (artifact?.id){
                def existingArtifact = Artifact.findById(artifact?.id)
                if (existingArtifact) {
                    existingArtifact.lock()
                    def existingFullPathAndFileName =
                        existingArtifact.serverFilePath + File.separator + existingArtifact.serverFileName
                    def existingFile = new File(existingFullPathAndFileName)
                    if (existingFile.exists()){
                        existingFile.delete()
                    }
                    existingArtifact = storeArtifactFile(existingArtifact, bytes, folderName)
                    existingArtifact.fileName = artifact.fileName
                    existingArtifact.description = artifact.description
                    existingArtifact.save(flush: true)
                }
            } else {
                artifact = storeArtifactFile(artifact, bytes, folderName)
                artifact.save(flush: true)
            }
        }
        return artifact
    }

    public void deleteArtifact(long artifactId){
        def existingArtifact = Artifact.findById(artifactId)
        if (existingArtifact) {
            existingArtifact.lock()
            def existingFullPathAndFileName =
                existingArtifact.serverFilePath + File.separator + existingArtifact.serverFileName
            def existingFile = new File(existingFullPathAndFileName)
            if (existingFile.exists()){
                existingFile.delete()
            }
            existingArtifact.delete(flush: true)
        }
    }

    private Artifact storeArtifactFile(Artifact artifact, byte[] bytes, String folderName){
        artifact.serverFilePath = subfolderDictionary[folderName]
        artifact.serverFileName = UUID.randomUUID().toString()
        artifact.size = bytes.length
        def fullPathAndFileName = artifact.serverFilePath + File.separator + artifact.serverFileName
        def fileOutputStream = new FileOutputStream(fullPathAndFileName)
        fileOutputStream.write(bytes)
        fileOutputStream.close()
        return artifact
    }

}
