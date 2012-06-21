package bumblebee

/**
 * Created with IntelliJ IDEA.
 * User: pascherk
 * Date: 6/18/12
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
class FeatureStatus implements Comparable<FeatureStatus>{
    Integer priority
    String status

    def int compareTo(FeatureStatus featureStatus){
        return this.priority.compareTo(featureStatus.priority)
    }

    def String toString(){
        return status
    }

    public static FeatureStatus createLowestPriorityStatus(){
        return new FeatureStatus( priority:10,
            status:'not started')
    }

    public static FeatureStatus createHighestPriorityStatus(){
        return new FeatureStatus( priority:60,
                status:'completed')
    }
}

class FeatureStatusMap {
    private static HashSet<FeatureStatus> allStatuses

    public static HashSet<FeatureStatus> getAllStatus(){
        if (!allStatuses){
            allStatuses = new HashSet<FeatureStatus>()
            def notStarted = new FeatureStatus(priority: 10, status: 'not started')
            allStatuses.add(notStarted)
            def inProgress = new FeatureStatus(priority: 20, status: 'in development')
            allStatuses.add(inProgress)
            def readyForDev = new FeatureStatus(priority: 30, status: 'ready for promotion')
            allStatuses.add(readyForDev)
            def rework = new FeatureStatus(priority: 35, status: 'in rework')
            allStatuses.add(rework)
            def readyForTest = new FeatureStatus(priority: 40, status: 'ready for test')
            allStatuses.add(readyForTest)
            def inTest = new FeatureStatus(priority: 45, status: 'in test')
            allStatuses.add(inTest)
            def failed = new FeatureStatus(priority: 50, status: 'failed')
            allStatuses.add(failed)
            def completed = new FeatureStatus(priority: 60, status: 'completed')
            allStatuses.add(completed)
        }
        return allStatuses
    }

    public static List<String> getAllStatusStrings(){
        def sortedList = new Vector<String>()
        for(FeatureStatus featureStatus in getAllStatus().sort()){
            sortedList.add(featureStatus.status)
        }
        return sortedList
    }

    public static FeatureStatus findByStatus(String status){
        def featureStatus = new FeatureStatus()
        if (status.trim().length() > 0){
            for(def lookupFeatureStatus in getAllStatus()){
                if (lookupFeatureStatus.status == status){
                    featureStatus = lookupFeatureStatus
                    break
                }
            }
        }
        return featureStatus
    }

}