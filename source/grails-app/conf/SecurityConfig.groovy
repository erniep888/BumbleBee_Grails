security {
    // see DefaultSecurityConfig.groovy for all settable/overridable properties
    active = true
    useNtlm = true
    ntlm.stripDomain = false
    ntlm.defaultDomain = "martinmarietta.com"
    ntlm.netbiosWINS = "10.100.2.1"
    loginUserDomainClass = "bumblebee.User"
    authorityDomainClass = "bumblebee.Role"
    requestMapClass = "bumblebee.Requestmap"
}
