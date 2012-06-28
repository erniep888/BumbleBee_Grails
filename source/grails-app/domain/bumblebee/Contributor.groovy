package bumblebee

//
// A Contributor is a person that can be assigned work.
class Contributor implements Comparable<Contributor> {
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

    int compareTo(Contributor user) {
        return this.username.compareTo(user.username)
    }
}
