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
