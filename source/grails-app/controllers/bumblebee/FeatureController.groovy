package bumblebee

class FeatureController {

    static defaultAction = "list"

    def list() {
        [featureInstanceList: Feature.list()]
    }

    def create() {
        [featureInstance: new Feature()]
    }

    def edit() {
        [featureInstance: new Feature()]
    }

    def deleteWarn() {
        [featureInstance: new Feature()]
    }

    def delete() {
        [featureInstance: new Feature()]
    }

    def save() {
        [featureInstance: new Feature()]
    }


    /***************** Partial View Actions Below ********************/

    def userList(Feature feature, String userType){
        SortedSet uniqueUserSet = new TreeSet<String>()
        for(FeaturePhase featurePhase in feature.featurePhases){
            if (userType.equalsIgnoreCase("developer"))
                uniqueUserSet.add(featurePhase.developer)
            else
                uniqueUserSet.add(featurePhase.tester)
        }
        def users = ""
        for( uniqueUser in uniqueUserSet){
            users += uniqueUser + " "
        }
        render(users.trim())
    }
}
