package bumblebee

// This is used to associate the FeaturePhase with
// the bug id's within the bug tracking system
class MantisBugId implements Comparable<MantisBugId>
{
    String bugId

    // static hasMany = []
    static belongsTo = [featurePhase:FeaturePhase]
    
    static constraints = {
        bugId(nullable: false, blank:false)
    }


    String toString()
    {
        return "${bugId}"
    }

    def int compareTo(MantisBugId mantisBug){
        return this.bugId.compareTo(mantisBug.bugId)
    }
}
