package bumblebee

class ActiveDirectorySettings {

    String hostname
    String port
    String bindDn
    String bindPassword
    String keystorePath
    String ldapSearchString    //  "DC=scrumtime,DC=com"
    // static hasMany = []
    // static belongsTo = []
    // static constraints = {}

    String toString ()
    {
        return "ActiveDirectorySettings ${id}"
    }
}
