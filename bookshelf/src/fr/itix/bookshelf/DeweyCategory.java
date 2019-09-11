/*
 * DeweyCategory.java
 *
 * Created on January 22, 2007, 9:16 PM
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
public class DeweyCategory {
    private String id;
    private String name;
    
    /** Creates a new instance of DeweyCategory */
    public DeweyCategory(SearchResult res) {
        try {
            Attributes attrs = res.getAttributes();
            
            Attribute idAttr = attrs.get("frItixCategoryId");
            this.id = (String) idAttr.get();
            
            Attribute nameAttr = attrs.get("frItixCategoryName");
            this.name = (String) nameAttr.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String toString() {
        return "[" + this.getId() + "] \"" + this.getName() + "\"";
    }
}
