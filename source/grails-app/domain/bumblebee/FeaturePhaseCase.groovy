package bumblebee

class FeaturePhaseCase implements Comparable<FeaturePhaseCase> {
    Date dateCreated
    Date lastUpdated
    Vendor vendor
    String href
    String caseIdentifier
    FeaturePhaseCaseStatus status
    String description

    static belongsTo = [featurePhase : FeaturePhase]

    static constraints = {
        caseIdentifier(nullable: false)
        status(nullable: false)
        description(nullable: true)
        href(nullable: true)
        vendor(nullable: false)
    }

    def String toString(){
        return vendor + " " + caseIdentifier
    }

    def int compareTo(FeaturePhaseCase featurePhaseCase){
        return this.caseIdentifier.compareTo(featurePhaseCase.caseIdentifier)
    }
}
