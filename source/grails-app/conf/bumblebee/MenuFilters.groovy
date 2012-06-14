package bumblebee

class MenuFilters {

    def filters = {
        dashboardMain(controller:'dashboard', action:'*') {
            after = { Map model ->
                model?.mainMenuSelection = 'dashboard'
            }
        }
        featureMain(controller:'feature', action:'*') {
            after = { Map model ->
                model?.mainMenuSelection = 'feature'
            }
        }
        libraryMain(controller:'library', action:'*') {
            after = { Map model ->
                model?.mainMenuSelection = 'library'
            }
        }
        administrationMain(controller:'administration', action:'*') {
            after = { Map model ->
                model?.mainMenuSelection = 'administration'
            }
        }
    }
}
