class UrlMappings {

	static mappings = {
        "/$controller/$action/$featureId/$id"{
            constraints {
                featureId(matches: "\\d+")
                id(matches: "\\d+")
            }
        }
        "/$controller/$action/$featureId/$artifactId/$id"{
            constraints {
                featureId(matches: "\\d+")
                artifactId(matches: "\\d+")
                id(matches: "\\d+")
            }
        }
        "/$controller/$action?/$id?"{
            constraints {
                id(matches: "\\d+")
            }
        }
		"/"(controller: "dashboard")
		"500"(view:'/error')
	}
}
