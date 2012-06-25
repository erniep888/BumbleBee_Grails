package bumblebee

class Vendor implements Comparable<Vendor>{

    String name
    String description
    Integer priority

    static constraints = {
        name(nullable: false, blank: false)
        description(nullable: true)
        priority(nullable: false)
    }

    def String toString(){
        return name
    }

    def int compareTo(Vendor vendor){
        return this.priority.compareTo(vendor.priority)
    }
}
