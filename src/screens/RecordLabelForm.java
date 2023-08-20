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
public final class RecordLabelForm extends GenericLabelForm {
    private QuestionForm sureUseComLab;
        
    /** Creates a new instance of HelpScreen */
    public RecordLabelForm(RecordForm recordForm) {
        super (Strings.LABELFORM, Constants.SLABEL, recordForm);
        sureUseComLab = new QuestionForm (Strings.SURECOMMONLABEL, this, this);
        addCommand(Constants.SETCOMLABCOMMAND);
    }    

    public void OKAction() {
        String[] labels = getTFValueStrings();
        record.setLabels(labels);
        ((RecordForm) back).setLabels(labels);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == Constants.SETCOMLABCOMMAND) {
            sureUseComLab.askQuestion(1);
            sureUseComLab.showScreen();                        
        } else super.commandAction(c, d);
    }

    public void deleteElementAt(int index) {
        if (index == 1) {
            record.setLabels(null);
            ((RecordForm) back).setLabels(null);
            back.showScreen();        
        }
        super.deleteElementAt(index);
    }

    protected String getValueAt(int i) {
        return ((RecordForm) back).getLabelAt(i);
    }    
}
