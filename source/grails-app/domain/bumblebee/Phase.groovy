package bumblebee

class Phase implements Comparable<Phase>{

    String name
    Integer displayOrder

    static constraints = {
        name(unique: true, nullable: true)
    }

    def int compareTo(Phase phase){
        return this.displayOrder.compareTo(phase.displayOrder)
    }
}
