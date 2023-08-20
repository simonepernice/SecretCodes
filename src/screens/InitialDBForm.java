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
 * openDBForm.java
 *
 * Created on 6 dicembre 2003, 21.55
 */
import data.DataBase;
import data.Record;
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone
 * @version
 */
public final class InitialDBForm extends GenericForm {
    
    private TextField tfName, tfPassword, tfPasswordc;
    private RecordsList recordsList;
    private Message errorLoading, errorCreating, errorReading, errorPassword;
    private DataBasesList dataBasesList;
    private DataBase dataBase;
    
    public InitialDBForm(DataBasesList dataBasesList) {
        super (Strings.CONFIRMDATA);                
        
        this.dataBasesList = dataBasesList;

        errorPassword = new Message(Strings.WARNING, Strings.PASSWORDSERROR, null, this);        
        errorCreating = new Message(Strings.WARNING, Strings.UNABLECREATEDB, null, this);
        errorLoading = new Message(Strings.WARNING, Strings.UNABLELOADDB, null,this);   
        errorReading = new Message(Strings.WARNING, Strings.UNABLEREAD, null, this);
    
        
        tfName = new TextField (Strings.NAME, "", Constants.SNAME, TextField.ANY);                
        tfPassword = new TextField (Strings.PASSWORD, "", Constants.SPASSWORD, TextField.PASSWORD|TextField.ANY);
        tfPasswordc = new TextField (Strings.CONFIRMPASSWORD, "", Constants.SPASSWORD, TextField.PASSWORD|TextField.ANY);

        append(tfName);
        append(tfPassword);        
        
        addCommand(Constants.CANCELCOMMAND);
    }
    
    public void create () {
        addCommand(Constants.CREATECOMMAND);           
        removeCommand(Constants.OPENCOMMAND);           
        tfName.setString("");   
        tfPassword.setString("");
        tfPasswordc.setString("");
        if (size() < 3) append (tfPasswordc);
    }
    
    public void open (String fname) {
        addCommand(Constants.OPENCOMMAND);
        removeCommand(Constants.CREATECOMMAND);
        tfName.setString(fname);         
        tfPassword.setString("");   
        if (size() > 2) delete (2);
    }
       
    public final void commandAction(Command c, Displayable s) {  
        if (c == Constants.CANCELCOMMAND) dataBasesList.showScreen();
        else if (c == Constants.CREATECOMMAND) {       
            if (! tfPassword.getString().equals(tfPasswordc.getString())) {
                errorPassword.showScreen();
            } else {
                Record.setStandardStatus(); //it may be modified working on a previous DB
                dataBase = DataBase.createDataBase (tfName.getString(), tfPassword.getString());
                if (dataBase != null) {//createDataBase dataBase
                    dataBasesList.orderedInsert(tfName.getString());
                    recordsList = new RecordsList (dataBase, dataBasesList);  
                    recordsList.initalizeLabels();
                } else errorCreating.showScreen();
            }
        } else { //if (c== Strings.OPENCOMMAND) {
            Record.setStandardStatus(); //it may be modified working on a previous DB
            dataBase = DataBase.openAndLoadDataBase (tfName.getString(), tfPassword.getString());
            if (dataBase != null) {
                if (dataBase.isUnReadable()) errorReading.showScreen();
                else {
                    recordsList = new RecordsList (dataBase, dataBasesList);
                    if (tfPassword.getString().length() == 0) recordsList.noCyphering();
                    else if (dataBase.isOldVersion()) recordsList.oldDBVersion();
                    else recordsList.showScreen();
                }
            } else errorLoading.showScreen();
        } 
    }    

    public DataBase getDataBase() {
        return dataBase;        
    }
}
