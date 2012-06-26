package bumblebee

// TODO: Mantis is currently setup in the resources.groovy file.
// This needs to pull its information from the database.
class BugSystemSettings {
    String systemName
    String bugAccessUrl

    String driverClassName
    String url
    String username
    String password

    static constraints = {
        systemName(nullable: false, blank: false)
        bugAccessUrl(nullable: false, blank: false)
        driverClassName(nullable: true)
        url(nullable: true)
        username(nullable: true)
        password(nullable: true)
    }

    String toString ()
    {
        return "${systemName}"
    }
}
