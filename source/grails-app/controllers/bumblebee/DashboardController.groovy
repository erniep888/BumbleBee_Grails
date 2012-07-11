package bumblebee

class DashboardController {

    def index() { }

    /**********JSON Actions***********/
    def allFeatureStatusCount() {
        def statuses = FeaturePhaseStatus.getAll()
        def totalPotentialStatuses = Phase.count() * Feature.count()
        def totalActualStatuses = 0
        def flotrGraphDataCollection = new FlotrGraphDataCollection()

        for (def status in statuses ) {
            int count = FeaturePhase.countByStatus(status)
            def flotrGraphData = new FlotrGraphData(label: status.status, x: 0, y: count)
            flotrGraphDataCollection.add(flotrGraphData)
            totalActualStatuses += count
        }
        if (totalPotentialStatuses > totalActualStatuses){
            def difference = totalPotentialStatuses - totalActualStatuses   // add difference to (not started)
            flotrGraphDataCollection.elementAt(0).y += difference
        }
        render(flotrGraphDataCollection.toJson())
    }

    def statusCompleteByCategory() {
        def query = Feature.where {}.projections { distinct 'category' }
        def categories = query.list()


        def flotrGraphDataCollection = new FlotrGraphDataCollection()
        for (def category in categories){
            // find the completion status by category
            def flotrGraphData = new FlotrGraphData(label: category,
                x: 3, y: 4)
            flotrGraphDataCollection.add(flotrGraphData)
        }
//
//        def statuses = FeaturePhaseStatus.getAll()
//        def statusCounts = new FlotrGraphDataCollection()

//        for (def status in statuses ) {
//            int count = FeaturePhase.countByStatus(status)
//            def statusCount = new FlotrGraphData(label: status.status, x: 0, y: count)
//            statusCounts.add(statusCount)
//        }
        render(flotrGraphDataCollection.toJson())
    }

    /**********Internal Classes**********/
    class FlotrGraphData {
        String label
        double x = 0
        double y = 0
        String toJson(){
            def result = '{"label":"' + label + '","data":[[' + x + ',' + y + ']]}'
            return result
        }
    }

    class FlotrGraphDataCollection extends Vector<FlotrGraphData>{
        String toJson(){
            def result = '{"counts":['
            for (def statusCount in this.elements()){
                result += statusCount.toJson() + ','
            }
            if (result.endsWith(','))
                result = result.substring(0,result.length() - 1)
            result += ']}'
            return result
        }
    }

}
