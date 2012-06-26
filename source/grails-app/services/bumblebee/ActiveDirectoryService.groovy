package bumblebee

import javax.naming.Context
import javax.naming.NamingEnumeration
import javax.naming.NamingException
import javax.naming.directory.DirContext
import javax.naming.directory.InitialDirContext
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult

class ActiveDirectoryService {

    boolean transactional = true

    def ActiveDirectoryUserInformation retrieveUserInformation(String username) {
        ActiveDirectoryUserInformation userInfo = null;

        DirContext ctx = connectToActiveDirectory();
        // Set up the search controls
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search subtree
        NamingEnumeration searchEnumeration = ctx.search("", //"DC=scrumtime,DC=com",
                "(&(objectCategory=Person)(sAMAccountName=" + username + "))", controls);
        if (searchEnumeration != null && searchEnumeration.hasMore()) {
            SearchResult searchResult = (SearchResult) searchEnumeration.next();
            userInfo = ActiveDirectoryUserInformation.fromSearchResult(searchResult);
        }

        return userInfo;
    }

    public List<ActiveDirectoryUserInformation> findUsers(String username, String firstName, String lastName) {
        List<ActiveDirectoryUserInformation> users = new Vector<ActiveDirectoryUserInformation>();
        ActiveDirectoryUserInformation userInfo = null;
        // Set up the search controls
        String queryString = "(&(objectCategory=Person)";
        if (username != null && username.length() > 0) {
            queryString += "(sAMAccountName=*" + username + "*)";
        }
        if (firstName != null && firstName.length() > 0) {
            queryString += "(givenName=*" + firstName + "*)";
        }
        if (lastName != null && lastName.length() > 0) {
            queryString += "(sn=*" + lastName + "*)";
        }
        queryString += ")";
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search subtree
        DirContext ctx = connectToActiveDirectory();
        NamingEnumeration searchEnumeration = ctx.search("", queryString, controls);

        while (searchEnumeration != null && searchEnumeration.hasMoreElements()) {
            SearchResult searchResult = (SearchResult) searchEnumeration.next();
            userInfo = ActiveDirectoryUserInformation.fromSearchResult(searchResult);
            if (userInfo.successfulLoad) {
                users.add(userInfo);
            }
        }

        return users;
    }

    private DirContext connectToActiveDirectory() throws NamingException {
        // Set up the environment for creating the initial context
        ActiveDirectorySettings adSettings = ActiveDirectorySettings.findById(1)
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

        // Specify LDAPS URL
        String url = "ldap://" + adSettings.hostname + ":" + adSettings.port + "/" + adSettings.ldapSearchString
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adSettings.bindDn);
        env.put(Context.SECURITY_CREDENTIALS, adSettings.bindPassword);

        // Create the initial context
        return new InitialDirContext(env);
    }
}
