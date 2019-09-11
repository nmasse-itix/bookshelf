/*
 * LdapDatabase.java
 *
 * Created on November 19, 2006, 11:38 AM
 *
 *
 * Bookshelf - Manage a virtual bookshelf
 *
 * Copyright (C) 2006 Nicolas MASSE <nicolas27.masse@laposte.net>
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package fr.itix.bookshelf;

import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;

/**
 * Represents an LDAP directory.
 *
 * @author Nicolas MASSE <nicolas27.masse@laposte.net>
 */
public class LdapDatabase implements BookshelfDatabase {
    private DirContext dctx;
    
    /** Creates a new instance of LdapDatabase */
    public LdapDatabase(String jndiUrl, String user, String pass) throws NamingException {
        //Create a Hashtable object to place environment settings
        Hashtable env = new Hashtable();
        
        //Specify service provider class
        String sp = "com.sun.jndi.ldap.LdapCtxFactory";
        env.put(Context.INITIAL_CONTEXT_FACTORY, sp);
        
        //Specify location of LDAP directory service
        env.put(Context.PROVIDER_URL, jndiUrl);
        
        //Set the authentication type
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        
        //Set username and password
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, pass);
        
        dctx = new InitialDirContext(env);
    }
    
    /**
     * Closes the connection to the directory.
     */
    protected void finalize() throws NamingException {
        dctx.close();
    }
    
    /*
     * See BookshelfDatabase.java
     */
    public BookshelfDocument getDocumentFromGlobalId(int globalId) {
        // Set search base
        String base = "";
        
        // Set attribute filter and search scope
        SearchControls sc = new SearchControls();
        String[] attributeFilter = { "frItixGlobalId", "frItixTitle" };
        sc.setReturningAttributes(attributeFilter);
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        // Define filter
        String filter = "(frItixGlobalId=" + globalId + ")";
        NamingEnumeration results;
        
        // The document
        BookshelfDocument d = null;
        
        try {
            results = dctx.search(base, filter, sc);
            if (results.hasMore()) {
                SearchResult sr = (SearchResult)results.next();
                d = new BookshelfDocument(sr);
                d.toString();
            } else {
                throw new RuntimeException("No matching document found");
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        
        return d;
    }
    
    /**
     * Returns the list of all the categories of the Decimal Dewey 
     * Classification under the given level.
     *
     * @param level the catagory in which we search
     */
    public List<DeweyCategory> getDeweyCategories(String level) {
        if ("".equals(level)) {
            // Set search base
            String base = "";
            
            // Set attribute filter and search scope
            SearchControls sc = new SearchControls();
            String[] attributeFilter = { "frItixCategoryId", "frItixCategoryName" };
            sc.setReturningAttributes(attributeFilter);
            sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            
            // Define filter
            String filter = "(objectClass=frItixCategory)";
            NamingEnumeration results;
            
            // The document
            List<DeweyCategory> categories = new ArrayList<DeweyCategory>();
            
            try {
                results = dctx.search(base, filter, sc);
                while (results.hasMore()) {
                    SearchResult sr = (SearchResult) results.next();
                    categories.add(new DeweyCategory(sr));
                }
            } catch (NamingException ex) {
                ex.printStackTrace();
            }
            
            return categories;
        }
        
        throw new RuntimeException("Not implemented");
    }
}
