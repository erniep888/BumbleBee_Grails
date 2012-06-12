package bumblebee

class Phase implements Comparable<Phase>{

    String name
    Integer priority

    static constraints = {
        name(unique: true, nullable: true)
    }

    def int compareTo(Phase phase){
        return this.priority.compareTo(phase.priority)
    }
}
