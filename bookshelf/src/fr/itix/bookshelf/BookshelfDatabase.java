/*
 * BookshelfDatabase.java
 *
 * Created on November 19, 2006, 11:36 AM
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

import java.util.List;

/**
 * Represents a connection to a database.
 *
 * @author Nicolas MASSE <nicolas27.masse@laposte.net>
 */
public interface BookshelfDatabase {
    
    public BookshelfDocument getDocumentFromGlobalId(int globalId);
    public List<DeweyCategory> getDeweyCategories(String level);
}
