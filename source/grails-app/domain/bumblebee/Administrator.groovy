package bumblebee

//
// An Administrator is a user that has administration privileges.
class Administrator implements Comparable<Administrator> {
    Date dateCreated
    Date lastUpdated

    String username
    String fullName

    static constraints = {
        username(nullable: false, blank: false)
        fullName(nullable: false)
    }

    int compareTo(Administrator administrator) {
        return this.username.compareTo(administrator.username)
    }
}
