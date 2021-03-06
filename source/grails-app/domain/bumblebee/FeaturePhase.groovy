package bumblebee

class FeaturePhase implements Comparable<FeaturePhase> {
    Phase phase
    Contributor developer
    Contributor tester
    Double developmentWorkEffort
    Double testWorkEffort
    FeaturePhaseStatus status
    Date executionDate
    String comments
    SortedSet tests
    SortedSet attachments
    SortedSet bugs
    SortedSet links
    SortedSet cases

    static belongsTo = [feature : Feature]

    static constraints = {
        phase(nullable: false)
        developer(nullable: true)
        tester(nullable: true)
        developmentWorkEffort(nullable: true, scale: 1)
        testWorkEffort(nullable: true, scale: 1)
        executionDate(nullable:  true)
        comments(nullable: true)
        status(nullable: false)
    }

    static hasMany = [tests: Artifact, attachments: Artifact,
            bugs: FeaturePhaseBug, links: Link, cases: FeaturePhaseCase]

    static mapping = {
        phase fetch: 'join'
    }

    def int compareTo(FeaturePhase featurePhase){
        return this.phase.compareTo(featurePhase.phase)
    }
}
