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
 * PasswordForm.java
 *
 * Created on 8 dicembre 2003, 10.55
 */
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone
 */
public final class PasswordForm extends GenericForm {
    
    private TextField tfNewpassword, tfNewpasswordc;
    private RecordsList recordsList;
    private Message errorPassword;
    
    /** Creates a new instance of PasswordForm */
    public PasswordForm(RecordsList recordsList) {
        super (Strings.INSERTPASSWORD);

        this.recordsList = recordsList;
        
        errorPassword = new Message (Strings.WARNING, Strings.PASSWORDSERROR, null, this);
        
        tfNewpassword = new TextField (Strings.PASSWORD, null, Constants.SPASSWORD, TextField.PASSWORD|TextField.ANY);
        tfNewpasswordc = new TextField (Strings.CONFIRMPASSWORD, null, Constants.SPASSWORD, TextField.PASSWORD|TextField.ANY);
        
        append(tfNewpassword);
        append(tfNewpasswordc);
        
        addCommand(Constants.OKCOMMAND);
        addCommand(Constants.CANCELCOMMAND);
    }
    
    /** This set the object as current display. A message is given in order to undersand what view.
     *  It is optionally given the index and the objet changed to make easier for the actived window
     *  to reflect the modification
     */
    public void setPassword () {
        changed = false;
        tfNewpassword.setString("");
        tfNewpasswordc.setString("");        
    }
    
    public final void commandAction(Command c, Displayable s) { 
        if (c == Constants.CANCELCOMMAND) {
            recordsList.showScreen();
        } else { //if (c == Constants.OKCOMMAND)
            if (!tfNewpassword.getString().equals(tfNewpasswordc.getString())) {
                errorPassword.showScreen();
            } else {
                if (changed && ! tfNewpassword.getString().equals(recordsList.getDataBase().getPassword())) {            
                    recordsList.getDataBase().setPassword(tfNewpassword.getString());
                    if (tfNewpassword.getString().length() == 0) recordsList.noCyphering();
                }
                recordsList.showScreen ();        
            }
        }
    }
}
