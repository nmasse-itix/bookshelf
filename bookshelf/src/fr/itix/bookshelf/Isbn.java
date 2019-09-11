/*
 * Isbn.java
 *
 * Created on November 19, 2006, 10:08 AM
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

/**
 * Represents an ISBN (10 or 13 chars long).
 *
 * @author Nicolas MASSE <nicolas27.masse@laposte.net>
 */
public abstract class Isbn {
    /** The ISBN without separator */
    private String isbn;
    
    /** Creates a new instance of Isbn */
    public Isbn(String newIsbn) {
        // Is the checksum valid ?
        if (this.check(newIsbn)) {

            // All is OK
            this.isbn = newIsbn;
        } else {
            throw new IllegalArgumentException("Bad Checksum");
        }
    }
    
    /**
     * Check the validity of the Isbn
     */
    public abstract boolean check(String isbn);
    
    /**
     * Returns the string representation of this ISBN.
     */
    public String toString() {
        return isbn;
    }
    
    /**
     * The factory of ISBNs.
     *
     * @param i an ISBN (10 or 13)
     * @return an object
     */
    public static Isbn newIsbn(String i) {
        // remove separators and length check
        i = Isbn.cleanIsbn(i);
        
        Isbn newIsbn = null;
        
        // An ISBN must be 10 or 13 chars long.
        if (i.length() == 10) {
            newIsbn = new Isbn10(i);
        } else if (i.length() == 13) {
            newIsbn = new Isbn13(i);
        } else {
            throw new IllegalArgumentException("The ISBN has an invalid size"
                    + " (" + i.length() + ")");
        }
        
        return newIsbn;
    }
    
    /**
     * Cleans the ISBN by removing all hyphens and white spaces.
     *
     * @param newIsbn the isbn to check
     * @return the cleaned isbn
     */
    public static String cleanIsbn(String newIsbn) {
        StringBuffer b = new StringBuffer(13);
        
        // Remove all the separators
        int len = newIsbn.length();
        for (int i = 0; i < len; i++) {
            
            char c = newIsbn.charAt(i);
            
            if ((c >= '0' && c <= '9') || c == 'X' || c == 'x') {
                // valid ISBN character
                b.append(c);
            } else if (c == '-' || c == ' ') {
                // ignore separators 
            } else {
                throw new IllegalArgumentException("Invalid character found in" 
                        + " the ISBN at pos " + i + " (" + c + ")");
            }
        }

        return b.toString();
    }
}

/**
 * An old ISBN (10 chars)
 */
class Isbn10 extends Isbn {
    public Isbn10(String i) {
        super(i);
    }

    /**
     * Check the validity of an ISBN10. 
     *
     * @param isbn the result of the function cleanIsbn.
     * @return true or false
     */
    public boolean check(String isbn) {
        boolean res = false;
        
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            if (isbn.charAt(i) == 'x' || isbn.charAt(i) == 'X') {
                throw new IllegalArgumentException("Invalid 'X' character found"
                        + " in the middle of the ISBN");
            }
            
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }
        
        if (isbn.charAt(9) == 'x' || isbn.charAt(9) == 'X') {
            sum += 10;
        } else {
            sum += (isbn.charAt(9) - '0');
        }
        
        if ((sum % 11) == 0) {
            res = true;
        }
        
        return res;
    }
}

/**
 * A new ISBN (13 chars).
 */
class Isbn13 extends Isbn {
    public Isbn13(String i) {
        super(i);
    }

    /**
     * Check the validity of an ISBN13. 
     *
     * @param s the result of the function cleanIsbn.
     * @return true or false
     */
    public boolean check(String s) {
        boolean res = false;
        
        int sum = 0;
        int w = 1;
        for (int i = 0; i < 13; i++) {
            if (s.charAt(i) == 'x' || s.charAt(i) == 'X') {
                throw new IllegalArgumentException("Invalid 'X' character found"
                        + " in an ISBN13");
            }
            
            sum += (s.charAt(i) - '0') * w;
            w = (w == 1 ? 3 : 1);
        }
        
        
        if ((sum % 10) == 0) {
            res = true;
        }
        
        return res;
    }
    
}