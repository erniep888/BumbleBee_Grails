package bumblebee

class Link implements Comparable<Link> {
    String name
    String href
    Boolean inNewWindow

    static belongsTo = [featurePhase : FeaturePhase]

    static constraints = {
        name(nullable: false)
        href(nullable: false)
        inNewWindow(nullable: false)
    }

    def int compareTo(Link link){
        return this.name.compareTo(link.name)
    }
}
