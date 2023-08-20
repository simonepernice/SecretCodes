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
public abstract class GenericList extends List implements CommandListener, ShowableScreen { 
    private static Display display;
        
    /** Creates a new instance of SetupForm */
    public GenericList(String title) {
        super (title, List.IMPLICIT);               
        
        this.setCommandListener(this);
    }       

    public void showScreen() {
        display.setCurrent(this);
    }    

    public static void setDisplay(Display dis) {
        if (display == null) display = dis;
    }
    
    public static Display getDisplay () {
        return display;
    }    
    
}
