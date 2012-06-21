package bumblebee

class Link implements Comparable<Link> {
    Date dateCreated
    Date lastUpdated

    String name
    String href
    Boolean inNewWindow

    static belongsTo = [featurePhase : FeaturePhase]

    static constraints = {
        name(nullable: false, blank: false)
        href(nullable: false, blank: false)
        inNewWindow(nullable: false)
    }

    def int compareTo(Link link){
        return this.name.compareTo(link.name)
    }
}
