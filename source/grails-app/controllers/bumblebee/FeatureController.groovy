package bumblebee

class FeatureController {

    static defaultAction = "list"

    def list() {
        [featureInstanceList: Feature.list()]
    }

    def create() {
        [featureInstance: new Feature()]
    }

    def edit(long id) {
        [featureInstance: Feature.findById(id)]
    }

    def deleteWarn(long id) {
        [featureInstance: Feature.findById(id)]
    }

    def delete(long id) {
        def feature = Feature.findById(id)
        feature.delete(flush: true)
        redirect(action: list())
    }

    def save() {
        def feature = new Feature(params)
        feature.validate()
        if (feature?.hasErrors()){
            if (!feature || !feature.id)
                render(view: "create", model: [featureInstance: feature])
            else
                render(view: "edit", model: [featureInstance: feature])
        } else {
            feature.save(flush: true)
            redirect(controller: "FeaturePhaseGeneral", params: [featureId: feature.id])
        }
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
