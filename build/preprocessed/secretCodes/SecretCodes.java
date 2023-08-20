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
