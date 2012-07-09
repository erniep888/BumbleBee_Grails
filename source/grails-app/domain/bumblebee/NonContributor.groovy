package bumblebee

//
// A Contributor is a person that can be assigned work.
class NonContributor implements Comparable<NonContributor> {
    Date dateCreated
    Date lastUpdated

    String username
    String fullName
    String emailAddress

    static constraints = {
        username(nullable: false, blank: false)
        fullName(nullable: false)
        emailAddress(nullable: false, email: true)
    }

    public String toString(){
        return fullName
    }

    int compareTo(NonContributor user) {
        return this.username.compareTo(user.username)
    }
}
