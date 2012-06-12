package bumblebee

class LibraryArtifact {
    String folder
    String description
    Artifact item

    static constraints = {
        item(nullable: false)
    }
}
