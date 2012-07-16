package bumblebee

class SecurityFilters {
	def springSecurityService
	
    def filters = {
        all(controller:'*', action:'*') {
            before = {
				def auth = springSecurityService.authentication
				if (auth){
					String username = auth.principal
				} 
				else {
					
				}
				
				
			               
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
