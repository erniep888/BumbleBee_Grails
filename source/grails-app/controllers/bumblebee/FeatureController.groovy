package bumblebee

import java.text.SimpleDateFormat

class FeatureController {

    static defaultAction = "list"

    def list() {
        [featureInstanceList: Feature.findAll({isDeleted == false})]
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
        feature.isDeleted = true
        feature.save(flush: true)
        redirect(action: list())
    }

    def save() {
        def feature = new Feature(params)
        feature.project = Project.findById(1)
        feature.validate()
        if (feature.hasErrors()){
            if (!feature || !feature.id)
                render(view: "create", model: [featureInstance: feature])
            else
                render(view: "edit", model: [featureInstance: feature])
        } else {
            feature.save(flush: true)
            redirect(controller: "FeaturePhaseGeneral", params: [featureId: feature.id, id: 1])
        }
    }


    /***************** Partial View Actions Below ********************/

    def userList(){
        def userType = request.getAttribute("userType")
        def featureInstance = request.getAttribute("feature")
        SortedSet uniqueUserSet = new TreeSet<String>()
        for(FeaturePhase featurePhase in featureInstance?.featurePhases){
            if (userType.equalsIgnoreCase("developer"))
                uniqueUserSet.add((featurePhase.developer)?featurePhase.developer:"")
            else
                uniqueUserSet.add((featurePhase.tester)?featurePhase.tester:"")
        }
        def users = ""
        for( uniqueUser in uniqueUserSet){
            if (uniqueUser && uniqueUser.trim().length() > 0)
                users += uniqueUser + ', '
        }
        def userListWithTrailingComma = users.trim()
        if (userListWithTrailingComma.length() > 0)
            users = userListWithTrailingComma.substring(0, userListWithTrailingComma.length()-1)
        render(users)
    }

    def workEffort(){
        def featureInstance = request.getAttribute("feature")
        def workEffort = 0.0d;
        for(FeaturePhase featurePhase in featureInstance.featurePhases){
            workEffort += (featurePhase.developmentWorkEffort) ? featurePhase.developmentWorkEffort : 0d
            workEffort += (featurePhase.testWorkEffort) ? featurePhase.testWorkEffort : 0d
        }
        render(workEffort)
    }

    def featureStatus(){
        def featureInstance = request.getAttribute("feature")
        def numberOfPhases = Phase.count()
        def numberOfFeaturePhases = FeaturePhase.countByFeature(featureInstance)
        def lowestPriorityStatus = FeatureStatus.createLowestPriorityStatus()
        if (numberOfFeaturePhases == numberOfPhases)  {
            lowestPriorityStatus = FeatureStatus.createHighestPriorityStatus()
            for(FeaturePhase featurePhase in featureInstance.featurePhases){
                def currentStatus = FeatureStatusMap.findByStatus(featurePhase.status)
                if (lowestPriorityStatus.compareTo(currentStatus) > 0)
                    lowestPriorityStatus = currentStatus
            }
        }
        render(lowestPriorityStatus.status)
    }

    def featureCompletion(){
        Date mostRecentCompletion = null;
        def featureInstance = request.getAttribute("feature")
        def numberOfPhases = Phase.count()
        def numberOfFeaturePhases = FeaturePhase.countByFeature(featureInstance)
        if (numberOfFeaturePhases == numberOfPhases)  {

            for(FeaturePhase featurePhase in featureInstance.featurePhases){
                if (!featurePhase.executionDate)
                    break;
                else if (!mostRecentCompletion)
                    mostRecentCompletion = featurePhase.executionDate
                else if (mostRecentCompletion.compareTo(featurePhase.executionDate) < 0)
                    mostRecentCompletion = featurePhase.executionDate
            }
        }
        if (mostRecentCompletion){
            def simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy")
            render(simpleDateFormat.format(mostRecentCompletion))
        } else
            render ''
    }

    def offShore(){
        def featureInstance = request.getAttribute("feature")
        String offShoreString = ""
        for(FeaturePhase featurePhase in featureInstance.featurePhases){
            if (featurePhase.isOffShore){
                offShoreString = "off shore"
                break;
            }
        }
        render offShoreString
    }
}
