package bumblebee

class Project implements Comparable<Project> {
    String name
    String description
    SortedSet libraryAttachments

    static constraints = {
        name(unique: true, blank: false, nullable: false)
        description(nullable: true)
    }

    static hasMany = [features: Feature, libraryAttachments: Artifact]

    def int compareTo(Project project){
        return this.name.compareTo(project.name)
    }
}
