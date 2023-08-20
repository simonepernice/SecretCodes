/**
*   SecretNotes stores data encrypted with password
*   Copyright (C) 2003 Simone Pernice <pernice@libero.it>
*
*   This file is part of SecretNotes.
*
*   SecretNotes is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   SecretNotes is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*/


package data;
/*
 * ComparableString.java
 *
 * Created on 18 novembre 2007, 23.50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public final class ComparableString implements Comparable {
    private String value;
    
    public ComparableString (String s) {
        value = s;
    }
    
    public int compareTo(Object o) {
        return value.compareTo(((ComparableString) o).value);
    }    
    
    public String toString () {
        return value.toString();
    }
}
