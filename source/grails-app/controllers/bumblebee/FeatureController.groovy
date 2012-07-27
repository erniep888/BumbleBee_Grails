package bumblebee

import java.text.SimpleDateFormat
import grails.converters.JSON

class FeatureController {

    def mantisIntegrationService
    def cacheService

    def featureListJsonKey = 'featureListJson'
    def jsonRefreshDate = 'jsonRefreshDate'

    static defaultAction = "list"

    def list() {
        if (cacheService.lookup == null) {
            cacheService.lookup = {loadFeatureList()}
        }
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
        cacheService.invalidate()
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
            cacheService.invalidate()
            redirect(controller: "FeaturePhaseGeneral", params: [featureId: feature.id, id: 1])
        }
    }

    def refreshFeatureList(){
        cacheService.invalidate()
        redirect(action: list())
    }

    /***************** Partial View Actions Below ********************/
    def allFeatures() {
        // TODO: this is not the proper way to cache the json result, but the spring-cache was not working
        System.out.println(params)
        JSON featureListJson = cacheService.get(featureListJsonKey)
//        Date jsonRefresh = cacheService.get(jsonRefreshDate)
//        if (jsonRefresh){
//            def now = new Date()
//            if (now.after(jsonRefresh)){
//                featureListJson = loadFeatureListIntoSession()
//            } else {
//                featureListJson = cacheService.get(featureListJsonKey)
//            }
//        }  else {
//            featureListJson = loadFeatureListIntoSession()
//        }

        render(featureListJson)
    }

    public JSON loadFeatureList(){
        Calendar calendar = Calendar.getInstance()
        def featureListJson = getFeatureList()
        //cacheService.set(featureListJsonKey, featureListJson )
        //calendar.add(Calendar.MINUTE, 120)
        //cacheService.set(jsonRefreshDate, calendar.time)
        return featureListJson
    }

    private JSON getFeatureList(){
        def featureRows
        def features = Feature.findAllByIsDeleted(false, [max: 10, offset: 20])

        for (def feature in features.listIterator()){
            def featureRow = [
                    id: '<div class="font-small">' + feature.id + '</div>',
                    module: '<div class="font-small">' + feature.module + '</div>',
                    feature: '<div class="font-medium">' + buildNameAndLink(feature) + '</div>',
                    description: buildDescription(feature),
                    developer: buildUserList('developer', feature),
                    sme: buildUserList('sme', feature),
                    workEffort: createDoubleNumericOutput(buildWorkEffort(feature)),
                    status: buildStatusList(feature),
                    completed: buildCompletionDate(feature),
                    bugs: buildFeatureBugList(feature),
                    bugStatus: buildStatusList(feature),
                    bugSeverity: buildFeatureBugSeverityMaximum(feature),
                    thirdPartyCases: buildThirdPartyCases(feature)
            ]
            if (!featureRows)
                featureRows = [aaData:[featureRow]]
            else
                featureRows.aaData.add(featureRow)
        }
        return featureRows as JSON
    }

    private String createDoubleNumericOutput(double number){
        return '<div class="center">' + number + '</div>'
    }

    private String buildNameAndLink(Feature featureInstance){
        return link(controller:'featurePhaseGeneral', action: 'edit', params: [featureId: featureInstance.id, id: 1]) {featureInstance.name}
    }

    private String buildDescription(Feature featureInstance){
        return '<div class="font-small">' + featureInstance.description + '</div>'
    }
    private String buildUserList(String userType, Feature featureInstance){
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
        return '<div class="font-small">' + users + '</div>'
    }

    private double buildWorkEffort(Feature featureInstance){
        def workEffort = 0.0d
        for(FeaturePhase featurePhase in featureInstance.featurePhases){
            workEffort += (featurePhase.developmentWorkEffort) ? featurePhase.developmentWorkEffort : 0d
            workEffort += (featurePhase.testWorkEffort) ? featurePhase.testWorkEffort : 0d
        }
        return workEffort
    }

    private String buildStatusList(Feature featureInstance){
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
        return '<div class="center font-medium">' + status.trim() + '</div>'
    }

    private String buildCompletionDate(Feature featureInstance){
        Date mostRecentCompletion = null;
        def completionDate = ''
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
            completionDate = simpleDateFormat.format(mostRecentCompletion)
        }
        return '<div class="center font-small">' + completionDate + '</div>'
    }

    private String buildFeatureBugList(Feature featureInstance){
        def result = ''

        def phaseId = 1
        for(Phase phase in Phase.findAll().sort()){
            def featurePhase = FeaturePhase.findByFeatureAndPhase(featureInstance, phase)
            if (featurePhase)
                result += link(controller:'featurePhaseBug', action: 'edit', params: [featureId: featureInstance.id, id: featurePhase.phaseId] )
                        {FeaturePhaseBug.countByFeaturePhase(featurePhase)}  + ' '
            else
                result += link(controller:'featurePhaseBug', action: 'edit', params: [featureId: featureInstance.id, id: phaseId] )  {0} + ' '

            phaseId++
        }
        return '<div class="center font-medium">' + result.trim() + '</div>'
    }

    private String buildFeatureBugStatusList(Feature featureInstance){
        def result = ''
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
        return '<div class="center font-medium">' + result.trim() + '</div>'
    }

    private String buildFeatureBugSeverityMaximum(Feature featureInstance){
        def result = ''
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
        return '<div class="center font-small">' + result.trim() + '</div>'
    }

    private String buildThirdPartyCases(Feature featureInstance){
        def result = ''

        def phaseId = 1
        for(Phase phase in Phase.findAll().sort()){
            def featurePhase = FeaturePhase.findByFeatureAndPhase(featureInstance, phase)
            if (featurePhase)
                result += link(controller:'featurePhaseCase', action: 'edit', params: [featureId: featureInstance.id, id: featurePhase.phaseId] )
                        {FeaturePhaseCase.countByFeaturePhase(featurePhase)}  + ' '
            else
                result += link(controller:'featurePhaseCase', action: 'edit', params: [featureId: featureInstance.id, id: phaseId] )  {0} + ' '

            phaseId++
        }
        return '<div class="center font-medium">' + result.trim() + '</div>'
    }
    /***************** Partial View Actions Below ********************/

    def userList(){
        def userType = request.getAttribute("userType")
        def featureInstance = request.getAttribute("feature")
        render(buildUserList(userType, featureInstance))
    }

    def workEffort(){
        def featureInstance = request.getAttribute("feature")
        render(buildWorkEffort(featureInstance))
    }

    def featureStatus(){
        def featureInstance = request.getAttribute("feature")
        render(buildStatusList(featureInstance))
    }

    def featureCompletion(){
        def featureInstance = request.getAttribute("feature")
        render(buildCompletionDate(featureInstance))
    }

    def featureBugs(){
        def featureInstance = request.getAttribute("feature")
        render buildFeatureBugList(featureInstance)
    }

    def featureBugStatus(){
        def featureInstance = request.getAttribute("feature")
        render buildFeatureBugStatusList(featureInstance)
    }

    def featureBugSeverity(){
        def featureInstance = request.getAttribute("feature")
        render buildFeatureBugSeverityMaximum(featureInstance)
    }

    def featureThirdPartyCases(){
        def featureInstance = request.getAttribute("feature")
        render buildThirdPartyCases(featureInstance)
    }


}
