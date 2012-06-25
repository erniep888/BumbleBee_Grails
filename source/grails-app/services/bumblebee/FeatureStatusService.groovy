package bumblebee

class FeatureStatusService {

    public FeaturePhaseStatus getDefaultStatus(){
        return FeaturePhaseStatus.findOrCreateWhere(priority: 10, status: "not started")
    }


}
