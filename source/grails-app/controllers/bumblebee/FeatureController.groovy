package bumblebee

import java.text.SimpleDateFormat


class FeatureController {

    def mantisIntegrationService

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
                uniqueUserSet.add((featurePhase.developer)?featurePhase.developer.fullName:"")
            else
                uniqueUserSet.add((featurePhase.tester)?featurePhase.tester.fullName:"")
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
        def status = ''
        def phaseId = 1
        for(Phase phase in Phase.findAll().sort()){
            def featurePhase = FeaturePhase.findByFeatureAndPhase(featureInstance, phase)
            if (featurePhase)
                status += link(controller:'featurePhaseGeneral', action: 'edit', params: [featureId: featureInstance.id, id: featurePhase.phaseId] )
                        {featurePhase.status.shortReference}  + ' '
            else
                status += link(controller:'featurePhaseGeneral', action: 'edit', params: [featureId: featureInstance.id, id: phaseId] )  {'a'} + ' '

            phaseId++
        }
        render(status.trim())
    }

    def featureCompletion(){
        Date mostRecentCompletion = null;
        def featureInstance = request.getAttribute("feature")
        def numberOfPhases = Phase.count()
        def numberOfFeaturePhases = FeaturePhase.countByFeature(featureInstance)
        if (numberOfFeaturePhases == numberOfPhases)  {

            for(FeaturePhase featurePhase in featureInstance.featurePhases){
                if (!featurePhase.executionDate){
                    mostRecentCompletion = null
                    break
                }
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

    def featureBugs(){
        def result = ''

        def phaseId = 1
        def featureInstance = request.getAttribute("feature")
        for(Phase phase in Phase.findAll().sort()){
            def featurePhase = FeaturePhase.findByFeatureAndPhase(featureInstance, phase)
            if (featurePhase)
                result += link(controller:'featurePhaseBug', action: 'edit', params: [featureId: featureInstance.id, id: featurePhase.phaseId] )
                        {FeaturePhaseBug.countByFeaturePhase(featurePhase)}  + ' '
            else
                result += link(controller:'featurePhaseBug', action: 'edit', params: [featureId: featureInstance.id, id: phaseId] )  {0} + ' '

            phaseId++
        }
        render result.trim()
    }

    def featureBugStatus(){
        def result = ''
        def featureInstance = request.getAttribute("feature")
        MantisBugInformation leastCompleteBug = null
        for(Phase phase in Phase.findAll().sort()){
            def featurePhase = FeaturePhase.findByFeatureAndPhase(featureInstance, phase)
            if (featurePhase){
                if (featurePhase.bugs != null && featurePhase.bugs.size() > 0){
                    for (FeaturePhaseBug bug in featurePhase.bugs) {
                        def bugInformation = mantisIntegrationService.findMantisBugInformationById(bug.bugSystemId)
                        if (leastCompleteBug == null)
                            leastCompleteBug = bugInformation
                        else
                            leastCompleteBug = MantisBugInformation.getLeastCompleteStatus(leastCompleteBug, bugInformation)
                    }
                }
            }
        }

        if (leastCompleteBug != null)
            result = leastCompleteBug.statusAsString
        render result.trim()
    }

    def featureBugSeverity(){
        def result = ''
        def featureInstance = request.getAttribute("feature")
        MantisBugInformation mostServereBug = null
        for(Phase phase in Phase.findAll().sort()){
            def featurePhase = FeaturePhase.findByFeatureAndPhase(featureInstance, phase)
            if (featurePhase){
                if (featurePhase.bugs != null && featurePhase.bugs.size() > 0){
                    for (FeaturePhaseBug bug in featurePhase.bugs) {
                        def bugInformation = mantisIntegrationService.findMantisBugInformationById(bug.bugSystemId)
                        if (mostServereBug == null)
                            mostServereBug = bugInformation
                        else
                            mostServereBug = MantisBugInformation.getWorstSeverity(mostServereBug, bugInformation)
                    }
                }
            }
        }

        if (mostServereBug != null)
            result = mostServereBug.severityAsString
        render result.trim()
    }

    def featureThirdPartyCases(){
        def result = ''

        def phaseId = 1
        def featureInstance = request.getAttribute("feature")
        for(Phase phase in Phase.findAll().sort()){
            def featurePhase = FeaturePhase.findByFeatureAndPhase(featureInstance, phase)
            if (featurePhase)
                result += link(controller:'featurePhaseCase', action: 'edit', params: [featureId: featureInstance.id, id: featurePhase.phaseId] )
                        {FeaturePhaseCase.countByFeaturePhase(featurePhase)}  + ' '
            else
                result += link(controller:'featurePhaseCase', action: 'edit', params: [featureId: featureInstance.id, id: phaseId] )  {0} + ' '

            phaseId++
        }
        render result.trim()
    }


}
