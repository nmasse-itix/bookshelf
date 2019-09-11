/*
 * BookshelfDocument.java
 *
 * Created on November 5, 2006, 8:59 PM
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

import javax.naming.*;
import javax.naming.directory.*;

/**
 *
 * @author Nicolas MASSE <nicolas27.masse@laposte.net>
 */
public class BookshelfDocument {
    private int globalId;
    private String title;
    
    /** Creates a new instance of BookshelfDocument */
    public BookshelfDocument(SearchResult res) {
        try {
            Attributes attrs = res.getAttributes();
            
            Attribute gidAttr = attrs.get("frItixGlobalId");
            this.globalId = Integer.parseInt((String) gidAttr.get());
            
            Attribute titleAttr = attrs.get("frItixTitle");
            this.title = (String) titleAttr.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String toString() {
        return "[" + this.globalId + "] \"" + this.title + "\"";
    }

    public int getGlobalId() {
        return globalId;
    }

    public String getTitle() {
        return title;
    }
    
}
