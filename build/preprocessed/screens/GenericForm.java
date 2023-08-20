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
