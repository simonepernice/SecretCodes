package screens;
/*
 * Strings.java
 *
 * Created on 14 gennaio 2003, 22.25
 */
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Command;
/**
 *
 * @author  Simone Pernice Stefania Giaconia
 * @version 1.1
 *
 * Added ICLEANALL icon for the clean all functin
 */
public final class Constants {
    public static final Command OKCOMMAND = new Command (Strings.OKCOMMANDSTR, Command.BACK, 0);    
    public static final Command CLOSECOMMAND = new Command (Strings.CLOSECOMMANDSTR, Command.BACK, 0);  
    public static final Command NEWCOMMAND = new Command (Strings.NEWCOMMANDSTR, Command.OK, 1);
    public static final Command EXITCOMMAND = new Command (Strings.EXITCOMMANDSTR, Command.EXIT, 3);
    
    public static final Command CREATECOMMAND = new Command (Strings.CREATECOMMANDSTR, Command.OK, 2);    
    public static final Command OPENCOMMAND = new Command (Strings.OPENCOMMANDSTR, Command.OK, 2);
    
    public static final Command ADDCOMMAND = new Command (Strings.ADDCOMMANDSTR, Command.ITEM, 1);
    public static final Command CLONECOMMAND = new Command (Strings.CLONECOMMANDSTR, Command.ITEM, 2);
    public static final Command SETUPCOMMAND = new Command (Strings.SETUPCOMMANDSTR, Command.ITEM,  3);   
    public static final Command PASSWORDCOMMAND = new Command (Strings.PASSWORDCOMMANDSTR, Command.ITEM, 4);    
    public static final Command SYNCCOMMAND = new Command (Strings.SYNCCOMMANDSTR, Command.ITEM, 5);    
    public static final Command EDITLABELCOMMAND = new Command (Strings.EDITLABELCOMMANDSTR, Command.ITEM, 6);    
    public static final Command EDITCOMLABELCOMMAND = new Command (Strings.EDITCOMLABELCOMMANDSTR, Command.ITEM, 6);    
    public static final Command DELETEDBCOMMAND = new Command (Strings.DELETEDBCOMMANDSTR, Command.ITEM, 7);
                    
    public static final Command NOCOMMAND = new Command (Strings.NOCOMMANDSTR, Command.OK, 1);
    public static final Command YESCOMMAND = new Command (Strings.YESCOMMANDSTR, Command.EXIT, 2);
    public static final Command CANCELCOMMAND = new Command (Strings.CANCELCOMMANDSTR, Command.EXIT, 2);
        
    public static final Command ADDLABELCOMMAND = new Command (Strings.ADDLABELCOMMANDSTR, Command.ITEM, 1);    
    public static final Command DELLABELCOMMAND = new Command (Strings.DELLABELCOMMANDSTR, Command.ITEM, 2);   
    public static final Command SETCOMLABCOMMAND = new Command (Strings.COMLABCOMMANDSTR, Command.ITEM, 3);
    public static final Command DELETECOMMAND = new Command (Strings.DELETECOMMANDSTR, Command.ITEM, 4);
    public static final Command GENERATEPWDCOMMAND = new Command (Strings.GENERATECOMMANDSTR, Command.ITEM, 5);
    
    
    public static final int SNAME = 32;
    public static final int SPASSWORD= 32;
    public static final int SKEY= 32;
    public static final int SLABEL = 32;
    public static final int SVALUE= 256;
    public static final char STABULATOR = ';';
    public static final char TRUE = 't';
    public static final char FALSE = 'f';
    public static final String SEPARATOR = ": ";
    public static final String SECRETCODES = "SecretCode.txt";  
    public static final String SCEXT = ".scbr";
    public static final int SPASSWORDLEN = 3;
    public static final String[] STATUSKEYS = {"@#@#RecordsStatus£$%&/()", "@#@#Rec#Sta£$%&/(ver1.7.6)", "@#@#Rec#Sta£$%&/(ver1.7.7)"};
    public static final String[] KEYVALUE = {Strings.KEY, Strings.VALUE};
    public static final int CMPKEY = 0;
    public final static boolean[] SHWKEYVALUE = {true, true};    
        
    public static Image IHELP = null;
    public static Image IABOUT = null;
    public static Image IDB = null;
    public static Image ISIMONE = null;
    public static Image IKEY = null;
    public static Image IVALUE = null;
            
    public static void loadImages () {
        try {                                   
            IHELP = Image.createImage("/screens/help.png");
            IABOUT = Image.createImage("/screens/about.png");
            IDB = Image.createImage("/screens/db.png");
            ISIMONE = Image.createImage("/screens/Simone.png");
            IKEY = Image.createImage("/screens/key.png");
            IVALUE = Image.createImage("/screens/value.png");
        } catch (IOException ioe) {
            //Something goes wrong loading the icons 
//#mdebug
//#             System.out.println ("Somethings goes wrong loading the images");
//#enddebug
        }
    }    
}
