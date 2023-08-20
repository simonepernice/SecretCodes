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
import data.Record;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;

/*
 * SetUpForm.java
 *
 * Created on 17 novembre 2007, 19.13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public final class SetUpForm extends GenericForm {
    private ChoiceGroup cgShow, cgCompare, cgSort, cgCase, cgLabels;
    private RecordsList recordsList;
    
    public SetUpForm(RecordsList recordsList) {
        super (Strings.SETUP);
        
        this.recordsList = recordsList;                
        
        cgShow = new ChoiceGroup (Strings.SHOWRECORDS, ChoiceGroup.MULTIPLE);

        
        cgCompare = new ChoiceGroup (Strings.ORDER, ChoiceGroup.EXCLUSIVE);

        
        cgSort = new ChoiceGroup (Strings.SORT, ChoiceGroup.EXCLUSIVE);
        cgSort.append(Strings.ASCENDING, null);
        cgSort.append(Strings.DESCENDING, null);

        cgCase = new ChoiceGroup (Strings.CASE, ChoiceGroup.EXCLUSIVE);
        cgCase.append(Strings.IGNORE, null);
        cgCase.append(Strings.CONSIDER, null);
        
        cgLabels = new ChoiceGroup (Strings.LABELS, ChoiceGroup.EXCLUSIVE);
        cgLabels.append(Strings.SHOW, null);        
        cgLabels.append(Strings.HIDE, null);        
        
        append (cgShow);
        append (cgCompare);
        append (cgSort);
        append (cgCase);
        append (cgLabels);
        
        addCommand(Constants.OKCOMMAND);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (changed) {
            final int s = cgShow.size();
            boolean[] ws = new boolean[s];
            for (int i=0; i<s; ++i) {
                ws[i] = cgShow.isSelected(i);
            }
            Record.setWhatToShow(ws);
            Record.setWhatToCompare(cgCompare.getSelectedIndex());
            Record.setSortAscending(cgSort.isSelected(0));
            Record.setIgnoreCase(cgCase.isSelected(0));
            Record.setShowLabels(cgLabels.isSelected(0));

            recordsList.getDataBase().sortRecords();
            recordsList.rebuildList();
        }
        recordsList.showScreen();
    }       
    
    public void setSelection () {
        cgShow.deleteAll();
        cgCompare.deleteAll();
        String[] labels = Record.getCommonLabels();
        if (labels == null) labels = Constants.KEYVALUE;
        boolean[] ws = Record.getWhatToShow();       
        for (int i=0; i<labels.length; ++i) {
            cgShow.append(labels[i], null);
            if (i < ws.length) cgShow.setSelectedIndex(i, ws[i]);
            cgCompare.append(labels[i], null);                        
        }                
        if (Record.getWhatToCompare()<cgCompare.size()) cgCompare.setSelectedIndex(Record.getWhatToCompare(), true);
        cgSort.setSelectedIndex(Record.isSortAscending() ? 0 : 1, true);
        cgCase.setSelectedIndex(Record.isIgnoreCase() ? 0 : 1, true);
        cgLabels.setSelectedIndex(Record.isShowLabels() ? 0 : 1, true);
        changed = false;
    }
}
