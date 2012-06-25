package bumblebee

class FeaturePhaseStatus implements Comparable<FeaturePhaseStatus>{

    String status
    Integer priority

    static constraints = {
        status(nullable: false, blank: false)
        priority(nullable: false)
    }

    public static FeaturePhaseStatus createLowestPriorityStatus(){
        return new FeaturePhaseStatus( priority:10,
                status:'not started')
    }

    public static FeaturePhaseStatus createHighestPriorityStatus(){
        return new FeaturePhaseStatus( priority:60,
                status:'completed')
    }

    def String toString(){
        return status
    }

    def int compareTo(FeaturePhaseStatus featureStatus){
        return this.priority.compareTo(featureStatus.priority)
    }

}
