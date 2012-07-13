package bumblebee

class HeaderFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                if (request.remoteUser)
                    redirect(url: "http://myportal")
                else
                    redirect(url: "http://myprojects")
            }
            after = { Map model ->
                model?.currentProject = Project.findById(1)
            }
            afterView = { Exception e ->

            }
        }
    }
}
