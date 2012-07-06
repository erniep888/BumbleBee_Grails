package bumblebee

class MenuFilters {

    def filters = {
        dashboardMain(controller:'dashboard*', action:'*') {
            after = { Map model ->
                model?.mainMenuSelection = 'dashboard'
            }
        }
        featureMain(controller:'feature*', action:'*') {
            after = { Map model ->
                model?.mainMenuSelection = 'feature'
            }
        }
        libraryMain(controller:'library*', action:'*') {
            after = { Map model ->
                model?.mainMenuSelection = 'library'
            }
        }
        administrationMain(controller:'administration*', action:'*') {
            after = { Map model ->
                model?.mainMenuSelection = 'administration'
            }
        }

        featurePhaseAll(controller: 'featurePhase*', action: '*'){
            after = { Map model ->
                def selectedPhase = Phase.findById(params.id)
                model?.selectedPhase = selectedPhase
                model?.phases = Phase.list()
            }
        }

        featurePhaseGeneral(controller: 'featurePhaseGeneral', action: '*'){
            after = { Map model ->
                model?.featurePhaseMenuSelection = 'general'
            }
        }

        featurePhaseTests(controller: 'featurePhaseTest', action: '*'){
            after = { Map model ->
                model?.featurePhaseMenuSelection = 'tests'
            }
        }

        featurePhaseAttachments(controller: 'featurePhaseAttachment', action: '*'){
            after = { Map model ->
                model?.featurePhaseMenuSelection = 'attachments'
            }
        }

        featurePhaseBugs(controller: 'featurePhaseBug', action: '*'){
            after = { Map model ->
                model?.featurePhaseMenuSelection = 'bugs'
            }
        }

        featurePhaseLinks(controller: 'featurePhaseLink', action: '*'){
            after = { Map model ->
                model?.featurePhaseMenuSelection = 'links'
            }
        }

        featurePhaseCases(controller: 'featurePhaseCase', action: '*'){
            after = { Map model ->
                model?.featurePhaseMenuSelection = 'cases'
            }
        }

        administrationContributor(controller: 'administrationContributor', action: '*'){
            after = { Map model ->
                model?.administrationMenuSelection = 'contributors'
            }
        }

        administrationContributor(controller: 'administrationAdministrator', action: '*'){
            after = { Map model ->
                model?.administrationMenuSelection = 'administrators'
            }
        }

        administrationContributor(controller: 'administrationDeletedObject', action: '*'){
            after = { Map model ->
                model?.administrationMenuSelection = 'deletedObjects'
            }
        }
    }
}
