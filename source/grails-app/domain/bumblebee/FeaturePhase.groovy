package bumblebee

class FeaturePhase implements Comparable<FeaturePhase> {

    Phase phase
    String assignedTo
    Double workEffort
    String status
    Date executionDate
    String comments
    SortedSet tests
    SortedSet attachments
    SortedSet bugs
    SortedSet links
    SortedSet thirdPartyCases

    static constraints = {
        phase(nullable: false)
    }

    static hasMany = [tests: Artifact, attachments: Artifact,
            bugs: MantisBugId, links: Link, thirdPartyCases: ThirdPartyCase]

    def int compareTo(FeaturePhase featurePhase){
        return this.phase.compareTo(featurePhase.phase)
    }
}
