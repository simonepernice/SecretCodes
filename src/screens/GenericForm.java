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
 * SetupForm.java
 *
 * Created on 18 gennaio 2003, 15.33
 */

import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone Pernice Stefania Giaconia
 * @version 1.0
 */
public abstract class GenericForm extends Form implements ItemStateListener, CommandListener, ShowableScreen {

    private static Display display;
    public boolean changed;    
        
    /** Creates a new instance of SetupForm */
    public GenericForm(String title) {
        super (title);                
        
        this.setItemStateListener(this);
        this.setCommandListener(this);
    }
  
    public final void itemStateChanged (Item item) {
        changed = true;
    }      

    public final void showScreen() {
        display.setCurrent(this);
    }   
    
    public static void setDisplay(Display dis) {
        if (display == null) display = dis;
    } 
    
    public static Display getDisplay () {
        return display;
    }
}
