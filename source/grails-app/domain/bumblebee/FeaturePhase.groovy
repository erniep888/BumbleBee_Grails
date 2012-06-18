package bumblebee

class FeaturePhase implements Comparable<FeaturePhase> {
    Phase phase
    String developer
    String tester
    Double developmentWorkEffort
    Double testWorkEffort
    String status
    Date executionDate
    String comments
    SortedSet tests
    SortedSet artifacts
    SortedSet bugs
    SortedSet links
    SortedSet thirdPartyCases

    static belongsTo = [feature : Feature]

    static constraints = {
        phase(nullable: false)
        developer(nullable: true)
        tester(nullable: true)
        developmentWorkEffort(nullable: true)
        testWorkEffort(nullable: true)
        executionDate(nullable:  true)
        comments(nullable: true)
    }

    static hasMany = [tests: Artifact, artifacts: Artifact,
            bugs: MantisBugId, links: Link, thirdPartyCases: ThirdPartyCase]

    static mapping = {
        phase fetch: 'join'
    }

    def int compareTo(FeaturePhase featurePhase){
        return this.phase.compareTo(featurePhase.phase)
    }
}
