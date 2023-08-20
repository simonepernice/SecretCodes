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


/*
 * Message.java
 *
 * Created on 1 dicembre 2007, 22.20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package screens;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;

/**
 *
 * @author simone
 */
public class Message extends GenericForm {
    private ShowableScreen back;
    
    public Message(String title) {
        this (title, null, null, null);
    }    

    public Message(String title, String message) {
        this (title, message, null, null);
    }
    
    public Message(String title, String message, Image image) {
        this (title, message, image, null);
    }
    
    public Message(String title, String message, Image image, ShowableScreen showableScreen) {
        super (title);
        if (image != null) append (new ImageItem("Simone", image, ImageItem.LAYOUT_CENTER, ""));
        if (message != null) append (message);
        setBack(showableScreen);
    }

    public void setBack(ShowableScreen back) {
        this.back = back;
        if (back == null) removeCommand(Constants.OKCOMMAND);
        else addCommand(Constants.OKCOMMAND);
    }        
    
    public void setMessage (String message) {
        deleteAll();
        append (message);        
    }
    
    public void commandAction(Command command, Displayable displayable) {
        back.showScreen();
    }    
}
