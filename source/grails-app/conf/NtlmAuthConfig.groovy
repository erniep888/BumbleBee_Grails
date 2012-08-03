/*
 * jCIFS configuration setting for NTLMv1 HTTP authentication filter.
 * Enter data that fits your network.
 *
 * http.domainController:
 *     The IP address of any SMB server that should be used to authenticate HTTP clients
 * smb.client.domain:
 *     The NT domain against which clients should be authenticated
 *
 * Plugin accepts all jCIFS configuration properties.
 * See http://jcifs.samba.org/src/docs/ntlmhttpauth.html for more info
 */

/*
 * Plugin is active by default, set the property 'active' to false to disable it.
 *
active = true
 */

active = false

jcifs {
	http.domainController = '10.100.2.1' // ip address of ldap/AD server ... or use jcifs.netbios.wins
	smb.client.domain = 'martinmarietta.com'   // scrumtime.com
	smb.client.username = 'oversightdorservice@martinmarietta.com'    // admin@scrumtime.com
	smb.client.password = 'oversightdorservice1'                      // test01
}

/*
 * You can also use per-environment configuration feature.
 * (http://grails.org/doc/1.1.x/guide/3.%20Configuration.html)
 * 
environments {
	development {
		jcifs {
			http.domainController = 'DEV DC ADDRESS'
			smb.client.domain = 'DEV DOMAIN'
    		smb.client.username = 'USERNAME'
			smb.client.password = 'PASSWORD'
		}
	}

	production {
		jcifs {
			http.domainController = 'PROD DC ADDRESS1,DS ADDRESS2'
			smb.client.domain = 'PROD DOMAIN'
			smb.client.username = 'USERNAME'
			smb.client.password = 'PASSWORD'
		}
	}
}
*/
