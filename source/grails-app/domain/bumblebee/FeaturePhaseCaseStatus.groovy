package bumblebee

class FeaturePhaseCaseStatus implements Comparable<FeaturePhaseCaseStatus>{

    String status
    Integer priority
    String description
    String href

    static constraints = {
        status(nullable: false, blank: false)
        description(nullable: true)
        priority(nullable: false)
        href(nullable: true)
    }

    def String toString(){
        return status
    }

    def int compareTo(FeaturePhaseCaseStatus thirdPartyCaseStatus){
        return this.priority.compareTo(thirdPartyCaseStatus.priority)
    }
}
