package bumblebee

class ThirdPartyCase implements Comparable<ThirdPartyCase> {
    Date dateCreated
    Date lastUpdated

    String caseIdentifier
    String status
    String description

    static belongsTo = [featurePhase : FeaturePhase]

    static constraints = {
        caseIdentifier(nullable: false)
        status(nullable: false)
    }

    def int compareTo(ThirdPartyCase thirdPartyCase){
        return this.caseIdentifier.compareTo(thirdPartyCase.caseIdentifier)
    }
}
