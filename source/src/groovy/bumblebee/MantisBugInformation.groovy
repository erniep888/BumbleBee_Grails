package bumblebee

// This is used to capture the details of a given mantis bug
// directly from the Mantis MySQL database.
class MantisBugInformation implements Comparable<MantisBugInformation>
{
    Integer id
    String summary
    Short status
    Short severity
    Short priority
    Map<Short, String> severityMap
    Map<Short,String> statusMap
    Map<Short,String> priorityMap

    public MantisBugInformation(){
        fillSeverityMap()
        fillStatusMap()
        fillPriorityMap()
    }


    static MantisBugInformation createFromMap(def map) {
        def mantisBugInformation = new MantisBugInformation()
        if (map) {
            mantisBugInformation.id = map.id
            mantisBugInformation.summary = map.summary
            mantisBugInformation.status = map.status
            mantisBugInformation.severity = map.severity
            mantisBugInformation.priority = map.priority
        }
        return mantisBugInformation
    }

    String toString() {
        return "${id}"
    }

    String getSeverityAsString(){
        return severityMap[(int)severity]
    }

    String getStatusAsString(){
        return statusMap[(int)status]
    }

    String getPriorityAsString(){
        return priorityMap[(int)priority]
    }


    int compareTo(MantisBugInformation mantisBugInformation){
        return this.id.compareTo(mantisBugInformation.id)
    }

    static MantisBugInformation getLeastCompleteStatus(MantisBugInformation mantisBugInformation1,
        MantisBugInformation mantisBugInformation2){
        if (mantisBugInformation1.status > mantisBugInformation2.status)
            return mantisBugInformation1
        else
            return mantisBugInformation2
    }

    static MantisBugInformation getWorstSeverity(MantisBugInformation mantisBugInformation1,
                                                 MantisBugInformation mantisBugInformation2){
        if (mantisBugInformation1.severity > mantisBugInformation2.severity)
            return mantisBugInformation1
        else
            return mantisBugInformation2
    }


    private void fillSeverityMap() {
        severityMap = new HashMap<Short, String>()
        severityMap.put(10, "feature")
        severityMap.put(20, "trivial")
        severityMap.put(30, "text")
        severityMap.put(40, "tweak")
        severityMap.put(50, "minor")
        severityMap.put(60, "major")
        severityMap.put(70, "crash")
        severityMap.put(80, "block")
    }

    private void fillStatusMap() {
        statusMap = new HashMap<Short,String>()
        statusMap.put(10,"new")
        statusMap.put(20,"feedback")
        statusMap.put(30,"acknowledged")
        statusMap.put(40,"confirmed")
        statusMap.put(50,"assigned")
        statusMap.put(60,"to be tested")
        statusMap.put(70,"on hold")
        statusMap.put(80,"resolved")
        statusMap.put(90,"closed")
    }

    private void fillPriorityMap() {
        priorityMap = new HashMap<Short,String>()
        priorityMap.put(10,"none")
        priorityMap.put(20,"low")
        priorityMap.put(30,"normal")
        priorityMap.put(40,"high")
        priorityMap.put(50,"urgent")
        priorityMap.put(60,"immediate")
    }
}
