package screens;
/*
 * Class.java
 *
 * Created on 6 dicembre 2003, 15.28
 */

import secretCodes.SecretCodes;
import data.ComparableString;
import data.DataBase;
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone
 * @version
 */
public final class DataBasesList extends GenericList implements DeletableElements {
    
    private InitialDBForm initialDBForm;
    private Message help, about, errorDeleting;    
    private SecretCodes secretCodes;
    
    public DataBasesList(SecretCodes sc) {
        super(Strings.SECRETNOTE);
        
        secretCodes = sc; 
        
        initialDBForm = new InitialDBForm (this);        

        about = new Message (Strings.ABOUT, Strings.ABOUTINFO, Constants.ISIMONE, this);
        help = new Message (Strings.HELP, Strings.HELPINSTRUCTION, null, this);
        errorDeleting = new Message (Strings.WARNING, Strings.UNABLEDELETE, null, this);
        
        ComparableString[] dbNames = DataBase.dataBaseList();
        if (dbNames != null) for (int i=0; i < dbNames.length; ++i) append (dbNames[i].toString(), Constants.IDB);
                        
        append(Strings.HELP, Constants.IHELP);
        append(Strings.ABOUT, Constants.IABOUT);

        addCommand (Constants.NEWCOMMAND);
        addCommand (Constants.EXITCOMMAND);         
    }

    public void deleteElementAt(int index) {
        initialDBForm.getDataBase().saveAndCloseDataBase(); //the database has to be closed before being deleted
        if (DataBase.deleteDataBase (getString(index))) delete(index);
        else errorDeleting.showScreen();        
    }
        
    public void orderedInsert (String dbName) {
        final int s = size()-2;
        int i;
        for (i=0; i<s; ++i)
            if (dbName.compareTo(getString(i)) < 0) {
                insert (i, dbName, Constants.IDB);
                break;
            }
        if (i == s) insert (i, dbName, Constants.IDB);        
        setSelectedIndex(i, true); //selects the appended element
    }
    
    public final void commandAction(Command c, Displayable s) {
        if (c == Constants.EXITCOMMAND) secretCodes.destroyApp(true);
        else if (c == List.SELECT_COMMAND) {
            int i = getSelectedIndex();
            if (i == size()-1) about.showScreen();
            else if (i == size()-2) help.showScreen();
            else {
                initialDBForm.open (this.getString(i));
                initialDBForm.showScreen();
            }
        } else {//if (c == Constants.NEWCOMMAND) {
            initialDBForm.create();
            initialDBForm.showScreen();
        } 
    }        

    public int getIndex(String name) {
        for (int i = 0; i < size(); ++i)
            if (getString(i).equals(name)) return i;
        return -1; //internal error
    }

    public InitialDBForm getInitialDBForm() {
        return initialDBForm;
    }
}
