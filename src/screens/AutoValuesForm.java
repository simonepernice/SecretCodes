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


package screens;
/*
 * HelpScreen.java
 *
 * Created on 18 gennaio 2003, 21.48
 */
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone Pernice Stefania Giaconia
 * @version 1.0
 */
public abstract class AutoValuesForm extends GenericForm {     
    private final int CHARS; 
        
    /** Creates a new instance of HelpScreen */
    public AutoValuesForm (String title, int fieldChars) {
        super (title);        
        
        CHARS = fieldChars;      
    }        
    
    protected static boolean compareStringArrays (String[] a1, String[] a2) {
        if (a1 == null || a2 == null || a1.length != a2.length) return false;
        for (int i=0; i<a1.length; ++i)
            if (! a1[i].equals(a2[i])) return false;
        return true;
    }
    
    abstract protected String getLabelAt (int i);
    abstract protected String getValueAt (int i);

    protected TextField getTfValueAt(int i) {
        return (TextField) get(i);
    }
    
    protected void setNumberTFValues (int n) {        
        TextField tf;
        final int s = size();
        int i;
        for (i=0; i<n; ++i) if (i >= s) append (new TextField (getLabelAt(i), "", CHARS, TextField.ANY));        
        for (i=s-1; i>=n; --i) delete (i);
    }

    protected void initializeTFValues () {        
        TextField tf;
        int i;
        final int s = size();
        for (i=0; i<s; ++i) {
            tf = getTfValueAt(i);
            tf.setLabel(getLabelAt(i));
            tf.setString(getValueAt(i));
        }
        changed = true;
    }        
    
    protected String[] getTFValueStrings () {
        final int s = size();
        String[] result = new String [s];
        for (int i=0; i<s; ++i) result[i] = getTfValueAt(i).getString();
        return result;
    }

    protected void setTFValue(int i, String value) {
        getTfValueAt(i).setString(value);
        changed = true;
    }
}
