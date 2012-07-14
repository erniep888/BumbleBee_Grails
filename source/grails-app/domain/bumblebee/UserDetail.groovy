package bumblebee

class UserDetail extends User {
	
	String firstName
	String lastName
	
	static belongsTo = [user: User]

    static constraints = {
    }
}
