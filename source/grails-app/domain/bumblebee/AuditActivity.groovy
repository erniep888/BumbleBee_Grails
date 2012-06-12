package bumblebee

class AuditActivity {
    Date dateCreated
    Date lastUpdated
    String type
    String description
    String user
    // static hasMany = []
    // static belongsTo = []
    // static constraints = {}

    String toString()
    {
        return "${id}"
    }
}
