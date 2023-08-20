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
import data.Record;
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone Pernice Stefania Giaconia
 * @version 1.0
 */
public final class CommonLabelForm extends GenericLabelForm {     
        
    /** Creates a new instance of HelpScreen */
    public CommonLabelForm(RecordsList recordsList) {
        super (Strings.COMMONLABELFORM, Constants.SLABEL, recordsList);
    }    

    public void OKAction() {
        record.setLabels(getTFValueStrings());
        ((RecordsList) back).rebuildList();      
    }


    protected String getValueAt(int i) {
        return Record.getCommonLabel(i);
    }   
}
