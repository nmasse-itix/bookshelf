/*
 * IsbnTest.java
 * JUnit based test
 *
 * Created on November 19, 2006, 10:10 AM
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

/**
 *
 * @author Nicolas MASSE <nicolas27.masse@laposte.net>
 */
public class IsbnTest extends TestCase {
    
    public IsbnTest(String testName) {
        super(testName);
    }

    public void testConstructor() {
        try {
            Isbn i = Isbn.newIsbn("");
            this.fail("no exception thrown on empty ISBN");
        } catch (IllegalArgumentException ignored) {
            // normal behavior
        }

        try {
            Isbn i = Isbn.newIsbn("0306406152");
        } catch (Exception e) {
            this.fail("Exception thrown on valid ISBN - 1 [" + e + "]");
        }

        try {
            Isbn i = Isbn.newIsbn("0-306-40615-2");
        } catch (Exception e) {
            this.fail("Exception thrown on valid ISBN - 2 [" + e + "]");
        }

        try {
            Isbn i = Isbn.newIsbn("   0  306 40615 2  ");
        } catch (Exception e) {
            this.fail("Exception thrown on valid ISBN - 3 [" + e + "]");
        }

        try {
            Isbn i = Isbn.newIsbn("123456789012345");
            this.fail("no exception thrown on too long ISBN (15)");
        } catch (IllegalArgumentException ignored) {
            // normal behavior
        }

        try {
            Isbn i = Isbn.newIsbn("123456789012");
            this.fail("no exception thrown on bad ISBN (12 chars)");
        } catch (IllegalArgumentException ignored) {
            // normal behavior
        }

        try {
            Isbn i = Isbn.newIsbn("12345678");
            this.fail("no exception thrown on too short ISBN (8)");
        } catch (IllegalArgumentException ignored) {
            // normal behavior
        }

        try {
            Isbn i = Isbn.newIsbn("978-0-356-42615-0");
        } catch (Exception e) {
            this.fail("Exception thrown on valid ISBN13 -  [" + e + "]");
        }

    }
    
    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
}
