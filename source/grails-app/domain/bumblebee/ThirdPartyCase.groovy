package bumblebee

class ThirdPartyCase implements Comparable<ThirdPartyCase> {
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
