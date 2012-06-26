package bumblebee

class FeaturePhaseBug implements Comparable<FeaturePhaseBug>{

    String bugSystemId
    //BugTrackingSystem bugTrackingSystem

    // static hasMany = []
    static belongsTo = [featurePhase:FeaturePhase]

    static constraints = {
        bugSystemId(nullable: false, blank:false)
    }


    String toString()
    {
        return "${bugSystemId}"
    }

    def int compareTo(FeaturePhaseBug featurePhaseBug){
        return this.bugSystemId.compareTo(featurePhaseBug.bugSystemId)
    }
}
