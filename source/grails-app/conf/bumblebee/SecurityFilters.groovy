package bumblebee

class SecurityFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {
//                if (!session.user ){
//                    redirect(url: "http://www.google.com")
//                }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
