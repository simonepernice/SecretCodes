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


package secretCodes;
/*
 * SecretNotes.java
 *
 * Created on 6 dicembre 2003, 15.25
 */

import data.DataBase;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import screens.Constants;
import screens.*;


/**
 * An example MIDlet with simple "Hello" text and an Exit command.
 * Refer to the startApp, pauseApp, and destroyApp
 * methods so see how each handles the requested transition.
 *
 * @author  Simone
 * @version
 */
public final class SecretCodes extends MIDlet {    
    
    private DataBasesList dataBasesList = null;
    
    public SecretCodes() {
        Constants.loadImages();
        GenericForm.setDisplay (Display.getDisplay(this));
        GenericList.setDisplay (Display.getDisplay(this));
    }

    public void startApp() {        
        if (dataBasesList == null) dataBasesList = new DataBasesList (this);
        dataBasesList.showScreen ();
    }

    public void pauseApp() {
        dataBasesList.getInitialDBForm().getDataBase().saveAndCloseDataBase();
        dataBasesList.showScreen();
        notifyPaused();
    }

    public void destroyApp(boolean unconditional) {
        DataBase db = dataBasesList.getInitialDBForm().getDataBase();
        if (db != null) db.saveAndCloseDataBase();
        notifyDestroyed();
    }       
}
