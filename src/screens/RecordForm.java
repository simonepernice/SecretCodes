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
import data.RecordsStatus;
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone Pernice Stefania Giaconia
 * @version 1.0
 */
public final class RecordForm extends AutoValuesForm {     

    private int index;
    private String[] labels;
    private Record record;
    private QuestionForm sureDeleteRecord;
    private Message keyRequired;
    private RecordsList recordsList;
    private PasswordGeneratorForm passwordGeneratorForm;
    private RecordLabelForm labelForm;    
        
    /** Creates a new instance of HelpScreen */
    public RecordForm(RecordsList recordsList) {
        super (Strings.RECORD, Constants.SVALUE);
        
        this.recordsList = recordsList;

        sureDeleteRecord = new QuestionForm (Strings.SUREDELETERECORD, this, recordsList);
        passwordGeneratorForm = new PasswordGeneratorForm (this);
        labelForm = new RecordLabelForm (this);
        keyRequired = new Message (Strings.WARNING, Strings.KEYREQUIRED, null, this);                    
        
        addCommand (Constants.OKCOMMAND);    
        addCommand (Constants.EDITLABELCOMMAND);
        addCommand (Constants.GENERATEPWDCOMMAND);
        addCommand (Constants.CANCELCOMMAND);
    }    

    protected String getLabelAt(int i) {
        if (labels == null || i >= labels.length) {
            if (i == 0) return Strings.KEY;
            return new StringBuffer(Strings.VALUE).append(String.valueOf(i)).toString();
        }
        return labels[i];
    }

    protected String getValueAt(int i) {
        return record.getValue(i);
    }

    public void setLabels(String[] lab) {
        labels = lab;
        if (labels ==null) labels = Record.getCommonLabels(); 
        setNumberTFValues(labels == null ? 2 : labels.length);        
    }

    public void editRecord (Record record, int index) {        
        this.record = record;
        this.index = index;
        addCommand(Constants.DELETECOMMAND);
        setLabels (record.getLabels());
        initializeTFValues();
        changed = false;
    }
    
    public void createRecord (String[] labelsToUse) {
        record = new Record ();
        
        if (labelsToUse != null) {
            record.setLabels(labels = Record.cloneStringArray(labelsToUse));
        } else labels = Record.getCommonLabels();          
        
        this.removeCommand(Constants.DELETECOMMAND);
        
        setNumberTFValues(labels.length);     
        initializeTFValues();
        
        index = -1; //to remeber it is new
        changed = false;
    }    
    
    public final void commandAction(Command c, Displayable d) {
        if (c == Constants.GENERATEPWDCOMMAND) {
            passwordGeneratorForm.setNumberOfValues(labels);
            passwordGeneratorForm.showScreen();            
        } else if (c == Constants.OKCOMMAND) {
            String[] val = getTFValueStrings();
            if (changed || ! compareStringArrays(val, record.getValues())) {
                if (getTfValueAt(0).getString().length() > 0) {
                    record.setValues(val);                                
                    if (index<0) recordsList.orderedInsert(record);                    
                    else recordsList.setRecordAt (record,index);                    
                    recordsList.showScreen();
                } else keyRequired.showScreen();                
            } else recordsList.showScreen();
        } else if (c == Constants.EDITLABELCOMMAND) {
            String[] lbls = record.getLabels();
            if (lbls == null) lbls = Record.getCommonLabels();
            labelForm.setRecord (record, lbls);
            labelForm.showScreen();
        } else if (c == Constants.DELETECOMMAND) {
            sureDeleteRecord.askQuestion(index);
            sureDeleteRecord.showScreen();
        } else { //if (c== Constants.CANCELCOMMAND){
            recordsList.showScreen();
        }
    }      
}
