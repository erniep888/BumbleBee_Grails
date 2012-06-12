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

    public MantisBugInformation(){
        fillSeverityMap()
        fillStatusMap()
    }

    static mapping = {
        datasource('mantis')
        table(name: 'mantis_bug_table')
        id(insertable: false, updateable: false)
        version(false)
        autoTimestamp(false)
    }

    static transients = ["severityMap", "statusMap"]

    String toString() {
        return "${id}"
    }

    String getSeverityAsString(){
        return severityMap[severity]
    }

    String getStatusAsString(){
        return statusMap[status]
    }

    int compareTo(MantisBugInformation mantisBugInformation){
        return this.id.compareTo(mantisBugInformation.id)
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
}
