security {

	// see DefaultSecurityConfig.groovy for all settable/overridable properties

	active = true
    useNtlm = true
    ntlm.stripDomain = false
    ntlm.defaultDomin = "scrumtime.com"
    ntlm.domainController = "192.168.1.1"
    ntlm.netbiosWINS = "192.168.1.1"

	loginUserDomainClass = "User"
	authorityDomainClass = "Role"
	requestMapClass = "Requestmap"
}
