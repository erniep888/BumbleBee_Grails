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
            def flotrGraphData = new FlotrGraphData(label: status.status, xyArray: new FlotrXY[1] )
            flotrGraphData.xyArray[0] =  new FlotrXY(x: 0, y: count)
            flotrGraphDataCollection.add(flotrGraphData)
            totalActualStatuses += count
        }
        if (totalPotentialStatuses > totalActualStatuses){
            def difference = totalPotentialStatuses - totalActualStatuses   // add difference to (not started)
            flotrGraphDataCollection.elementAt(0).xyArray[0].y += difference
        }
        render(flotrGraphDataCollection.toJson())
    }

    def statusCompleteByCategory() {
        def query = Feature.where {}.projections { distinct 'category' }
        def categories = query.list()

        def flotrGraphDataCollection = new FlotrGraphDataCollection()
        // build the incomplete flotrXY array
        def categoryIndex = 0
        def flotrXYArrayIndex = 0
        def incompleteFlotrXYArray = new FlotrXY[categories.size()]
        for (def category in categories){
            // find the incompletion status by category
            def maxPriorityQuery = FeaturePhaseStatus.where { priority >= max(priority)}
            FeaturePhaseStatus completedStatus = maxPriorityQuery.find()
            def count = FeaturePhase.where{status != completedStatus && feature.category == category}.count()
            def flotrXY = new FlotrXY(x:categoryIndex, y: count)
            incompleteFlotrXYArray[flotrXYArrayIndex] = flotrXY
            flotrXYArrayIndex++
            categoryIndex += 2
        }
        def incompleteFlotrGraphData = new FlotrGraphData(label: 'Incomplete', xyArray: incompleteFlotrXYArray )
        flotrGraphDataCollection.add(incompleteFlotrGraphData)

        categoryIndex = 0
        flotrXYArrayIndex = 0
        def completeFlotrXYArray = new FlotrXY[categories.size()]
        for (def category in categories){
            // find the completion status by category
            def maxPriorityQuery = FeaturePhaseStatus.where { priority >= max(priority)}
            FeaturePhaseStatus completedStatus = maxPriorityQuery.find()
            def count = FeaturePhase.where{status == completedStatus && feature.category == category}.count()
            def flotrXY = new FlotrXY(x:categoryIndex, y: count)
            completeFlotrXYArray[flotrXYArrayIndex] = flotrXY
            flotrXYArrayIndex++
            categoryIndex += 2
        }
        def completeFlotrGraphData = new FlotrGraphData(label: 'Complete', xyArray: completeFlotrXYArray )
        flotrGraphDataCollection.add(completeFlotrGraphData)
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
    class FlotrXY {
        double x = 0
        double y = 0
    }
    class FlotrGraphData {
        String label
        FlotrXY[] xyArray
        String toJson(){
            def result = '{"label":"' + label + '","data":['
            for (FlotrXY flotrXY in xyArray){
                result +=  '[' + flotrXY.x + ',' + flotrXY.y + '],'
            }
            if (result.endsWith(','))
                result = result.substring(0,result.length() - 1)
            result += ']}'
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
