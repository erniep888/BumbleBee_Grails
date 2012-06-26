package bumblebee

//
// A Worker is a user that can be assigned work.
class Worker implements Comparable<Worker> {
    Date dateCreated
    Date lastUpdated

    String username
    String fullName

    static constraints = {
        username(nullable: false, blank: false)
        fullName(nullable: false)
    }

    public String toString(){
        return fullName
    }

    int compareTo(Worker worker) {
        return this.username.compareTo(worker.username)
    }
}
