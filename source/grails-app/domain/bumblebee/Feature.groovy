package bumblebee

class Feature implements Comparable<Feature> {

    String name
    String description
    String category  // the name that is used to organize the features into groups or categories
    Boolean isDeleted  // determines if someone performed a soft-delete of this
    SortedSet featurePhases  // ensures the featurePhases will be in DisplayOrder

    static constraints = {
        name(unique: true, nullable: false, blank: false)
        description(nullable: true)
        isDeleted(nullable:  false)
        category(nullable: true)
    }

    static hasMany = [featurePhases: FeaturePhase]

    def int compareTo(Feature feature){
        return this.name.compareTo(feature.name)
    }
}
