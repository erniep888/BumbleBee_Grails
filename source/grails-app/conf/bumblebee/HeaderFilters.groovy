package bumblebee

class HeaderFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {

            }
            after = { Map model ->
                model?.currentProject = Project.findById(1)
            }
            afterView = { Exception e ->

            }
        }
    }
}
