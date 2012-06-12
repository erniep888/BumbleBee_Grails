package bumblebee

class Feature implements Comparable<Feature> {

    String name
    String description
    SortedSet featurePhases  // ensures the featurePhases will be in DisplayOrder

    static constraints = {
        name(unique: true, nullable: false)
        description(nullable: true)
    }

    static hasMany = [featurePhases: FeaturePhase]

    def int compareTo(Feature feature){
        return this.name.compareTo(feature.name)
    }
}
