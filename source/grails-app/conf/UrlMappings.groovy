class UrlMappings {

	static mappings = {
        "/$controller/$action/$featureId/$id"{
            constraints {
                featureId(matches: "\\d+")
            }
        }
        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
