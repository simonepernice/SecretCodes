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
public final class QuestionForm extends GenericForm {     
    
    private int index;
    private DeletableElements showDeleteIfYes;
    private ShowableScreen showIfNo;
    private Message errorPassword;
    private TextField tfPassword;
    private String password;

    public QuestionForm(String title, ShowableScreen showIfNo, DeletableElements showDeleteIfYes) {
        this (title, showIfNo, showDeleteIfYes, null);
    }
    
    public QuestionForm(String title, ShowableScreen showIfNo, DeletableElements showDeleteIfYes, String password) {
        super (Strings.WARNING);
        
        this.showDeleteIfYes = showDeleteIfYes;
        this.showIfNo = showIfNo;
        this.password = password;
        
        errorPassword = new Message (Strings.ERROR, Strings.PASSWORWRONG, null, this);
                        
        append(title);
        
        if (password != null) {
            tfPassword = new TextField (Strings.CONFIRMPASSWORD, "", Constants.SPASSWORD, TextField.PASSWORD|TextField.ANY);
            append (tfPassword);
        }
        
        addCommand (Constants.YESCOMMAND);
        addCommand (Constants.NOCOMMAND);
    }
    
    public void askQuestion (int index) {
        this.index = index;
        if (tfPassword != null) tfPassword.setString("");
    }
    
    public final void commandAction(Command c, Displayable s) {
        if (c == Constants.NOCOMMAND) {
            showIfNo.showScreen ();
        } else {//if (c == Strings.YESCOMMAND
            if (tfPassword == null || tfPassword.getString().equals(password)) {
                showDeleteIfYes.deleteElementAt(index);
                showDeleteIfYes.showScreen();
            } else errorPassword.showScreen();
        }
    }      
}
