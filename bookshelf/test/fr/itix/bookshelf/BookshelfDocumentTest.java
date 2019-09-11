/*
 * BookshelfDocumentTest.java
 * JUnit based test
 *
 * Created on November 5, 2006, 9:16 PM
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

import junit.framework.*;
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;


/**
 * Asserts the behavior of the class BookshelfDocument
 *
 * @author Nicolas MASSE <nicolas27.masse@laposte.net>
 */
public class BookshelfDocumentTest extends TestCase {
    private DirContext dctx;
    
    /**
     * Creates a new test suite
     */
    public BookshelfDocumentTest(String testName) {
        super(testName);
    }

    /**
     * Establishes the connection to the LDAP server.
     */
    protected void setUp() throws Exception {
        //Create a Hashtable object to place environment settings
        Hashtable env = new Hashtable();

        //Specify service provider class
        String sp = "com.sun.jndi.ldap.LdapCtxFactory";
        env.put(Context.INITIAL_CONTEXT_FACTORY, sp);

        //Specify location of LDAP directory service
        String jndiUrl = "ldap://localhost:389/dc=bookshelf,dc=itix,dc=fr";
        env.put(Context.PROVIDER_URL,jndiUrl);

        //Specify an user and password
        String user = "cn=Manager,dc=bookshelf,dc=itix,dc=fr";
        String pwd = "secret";

        //Set the authentication type
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        //Set username and password
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, pwd);
        
        dctx = new InitialDirContext(env);
    }

    /**
     * Closes the connection to the LDAP server
     */
    protected void tearDown() throws Exception {
        dctx.close();
    }
    
    /**
     * Test if a document can be constructed from an LDAP search.
     */
    public void testConstructDocument() throws Exception {
        //Set search base
        String base = "";

        //Set attribute filter and search scope
        SearchControls sc = new SearchControls();
        String[] attributeFilter = {"frItixGlobalId", "frItixTitle"};
        sc.setReturningAttributes(attributeFilter);
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        //Define filter
        String filter = "(frItixTitle=*SELinux*)";

        //Perform search
        NamingEnumeration results = dctx.search(base, filter, sc);
        
        //results
        while (results.hasMore()) {
            SearchResult sr = (SearchResult)results.next();
            BookshelfDocument d = new BookshelfDocument(sr);
            d.toString();
        }
        
    }
    
    /**
     * Tests a failing construtor (frItixTitle missing).
     */
    public void testConstructDocument2() throws Exception {
        //Set search base
        String base = "";

        //Set attribute filter and search scope
        SearchControls sc = new SearchControls();
        String[] attributeFilter = {"frItixGlobalId"};
        sc.setReturningAttributes(attributeFilter);
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        //Define filter
        String filter = "(frItixTitle=*SELinux*)";

        //Perform search
        NamingEnumeration results = dctx.search(base, filter, sc);
        
        //results
        while (results.hasMore()) {
            SearchResult sr = (SearchResult)results.next();
            try {
                BookshelfDocument d = new BookshelfDocument(sr);
                fail("Should throw a RuntimeException");
            } catch (RuntimeException expected) {
                // Normal behavior
            }
        }
        
    }

    /**
     * Tests a failing construtor (frItixGlobalId missing).
     */
    public void testConstructDocument3() throws Exception {
        //Set search base
        String base = "";

        //Set attribute filter and search scope
        SearchControls sc = new SearchControls();
        String[] attributeFilter = {"frItixTitle"};
        sc.setReturningAttributes(attributeFilter);
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        //Define filter
        String filter = "(frItixTitle=*SELinux*)";

        //Perform search
        NamingEnumeration results = dctx.search(base, filter, sc);
        
        //results
        while (results.hasMore()) {
            SearchResult sr = (SearchResult)results.next();
            try {
                BookshelfDocument d = new BookshelfDocument(sr);
                fail("Should throw a RuntimeException");
            } catch (RuntimeException expected) {
                // Normal behavior
            }
        }
        
    }
}
