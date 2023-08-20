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
public abstract class GenericLabelForm extends AutoValuesForm implements DeletableElements {     

    private QuestionForm sureDeleteLabel;
    private Alert minimumReqs;
    protected ShowableScreen back;
    protected Record record;
        
    /** Creates a new instance of HelpScreen */
    public GenericLabelForm(String label, int chars, ShowableScreen back) {
        super (label, chars);
        
        this.back = back;

        sureDeleteLabel = new QuestionForm (Strings.SUREDELETELABEL, this, this);     
        minimumReqs = new Alert (Strings.WARNING, Strings.ATLEAST2VALUESREQ, null, AlertType.WARNING);
        
        addCommand (Constants.OKCOMMAND);            
        addCommand (Constants.ADDLABELCOMMAND);
        addCommand (Constants.DELLABELCOMMAND);
        addCommand (Constants.CANCELCOMMAND);
    }    

    public void deleteElementAt(int index) {
        setNumberTFValues(size()-1);
        changed = true;
    }
    
    public abstract void OKAction ();
    
    public void commandAction(Command c, Displayable d) {
        if (c == Constants.OKCOMMAND) {
            if (changed || ! compareStringArrays(getTFValueStrings(), record.getLabels())) OKAction();
            back.showScreen();
        } else if (c == Constants.ADDLABELCOMMAND) {
            final int s = size();
            setNumberTFValues(s+1);
            setTFValue(s, new StringBuffer(Strings.VALUE).append(String.valueOf(s)).toString());
        } else if (c == Constants.DELLABELCOMMAND) {
            if (size()>2) {
                sureDeleteLabel.askQuestion(0);
                sureDeleteLabel.showScreen();
            } else getDisplay().setCurrent(minimumReqs, this);
        } else { //if (c == Constants.CANCELCOMMAND) {
            back.showScreen();
        }
    }      

    protected String getLabelAt (int i) {
        if (i == 0) return Strings.KEYLABEL;
        return new StringBuffer(Strings.VALUE).append(String.valueOf(i)).append(Strings.LABEL).toString();
    }    
     
    public void setRecord(Record rec, String[] labels) {
        record = rec;
        record.setLabels(Record.cloneStringArray(labels));
        setNumberTFValues(labels != null ? labels.length : 2);
        initializeTFValues();
        changed = false;
    }
}
