package screens;
/*
 * Class.java
 *
 * Created on 6 dicembre 2003, 15.28
 */

import data.DataBase;
import data.Record;
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone
 * @version
 */
public final class RecordsList extends GenericList implements DeletableElements {
    
    private RecordForm recordForm;
    private PasswordForm passwordForm;
    private DataBase dataBase;
    private Message nosaved, noCyphering, oldVersion, creation;
    private SyncForm syncForm;
    private DataBasesList dataBasesList;
    private QuestionForm deleteCurrentDataBase;    
    private SetUpForm setUpForm;
    private Image image;
    private CommonLabelForm commonLabelForm;
    
    RecordsList(DataBase dataBase, DataBasesList dataBasesList) {
        super(dataBase.getName());
        
        this.dataBase = dataBase;       
        this.dataBasesList = dataBasesList;
        
        deleteCurrentDataBase = new QuestionForm (Strings.SUREDELETEDB, this, dataBasesList, dataBase.getPassword());        
        recordForm = new RecordForm(this);
        passwordForm = new PasswordForm(this);                        
        syncForm = new SyncForm (this);
        setUpForm = new SetUpForm (this);
        commonLabelForm = new CommonLabelForm(this);
        
        noCyphering = new Message(Strings.WARNING, Strings.NOTCYPHERED, null, this);        
        nosaved = new Message (Strings.SAVE, Strings.NOESAVEABLE, null, this);        
        oldVersion = new Message (Strings.WARNING, Strings.OLDVERSION, null, this);
        creation = new Message (Strings.WARNING, Strings.NEWCREATION, null, commonLabelForm);
        
        rebuildList ();
                
        addCommand (Constants.ADDCOMMAND);
        addCommand (Constants.CLONECOMMAND);
        addCommand (Constants.CLOSECOMMAND);
        addCommand (Constants.SYNCCOMMAND);     
        addCommand (Constants.PASSWORDCOMMAND);
        addCommand (Constants.DELETEDBCOMMAND);
        addCommand (Constants.SETUPCOMMAND);
        addCommand (Constants.EDITCOMLABELCOMMAND);
    }    
    
    public DataBase getDataBase () {
        return dataBase;
    }
    
    public void rebuildList () {
        deleteAll();
        
        image = Record.getImage();
        int end = dataBase.size(), i;
        for (i=0; i < end; ++i) append (dataBase.getRecordAt(i).toString(), image);         
    }      

    public void deleteElementAt(int index) {
        dataBase.removeRecordAt(index);
        delete(index);        
    }     

    public void orderedInsert (Record rec) {                
        int i = dataBase.addRecord(rec);
        insert(i, rec.toString(), image);        
        setSelectedIndex(i, true);  
    }    
    
    public void setRecordAt (Record rec, int index) {
        delete (index);
        index = dataBase.setRecordtAt(rec, index);
        insert(index, rec.toString(), image);
        setSelectedIndex(index, true);
    }
    
    public void noCyphering () {
        noCyphering.setBack(this);
        noCyphering.showScreen();
    }
    
    public void initalizeLabels () {
        Record r = dataBase.getRecordsStatus();
        commonLabelForm.setRecord(r, r.getLabels());        
        if (dataBase.getPassword().length() == 0) {
            noCyphering.setBack(creation);
            noCyphering.showScreen();
        } else creation.showScreen();
    }

    void oldDBVersion() {
        oldVersion.showScreen(); 
    }       
    
    public final void commandAction(Command c, Displayable s) {
        if (c == Constants.CLOSECOMMAND) {
            if (!dataBase.saveAndCloseDataBase ()) nosaved.showScreen();
            else dataBasesList.showScreen ();        
        } else if (c == Constants.PASSWORDCOMMAND) {
            passwordForm.setPassword();
            passwordForm.showScreen();//showScreen record changed        
        } else if (c == Constants.SYNCCOMMAND) {
            syncForm.addSyncWays();
            //syncForm.showScreen(); The show screen is automatic after the wait screen
        } else if (c == List.SELECT_COMMAND) {
            int i = getSelectedIndex();
            recordForm.editRecord (dataBase.getRecordAt(i), i); 
            recordForm.showScreen();
        } else if (c == Constants.ADDCOMMAND) {
            recordForm.createRecord (null);
            recordForm.showScreen();
        } else if (c == Constants.CLONECOMMAND) {
            int i = getSelectedIndex();
            if (i == -1) recordForm.createRecord(null);
            else recordForm.createRecord (dataBase.getRecordAt(i).getLabels());
            recordForm.showScreen();
        }  else if (c == Constants.SETUPCOMMAND) {
            setUpForm.setSelection();
            setUpForm.showScreen();
        } else if (c == Constants.EDITCOMLABELCOMMAND) {
            Record r = dataBase.getRecordsStatus();
            commonLabelForm.setRecord(r, r.getLabels());
            commonLabelForm.showScreen();
        } else { //if (c == Strings.DELETECOMMAND) {
            int i = dataBasesList.getIndex(dataBase.getName());
            if (i >= 0) {
                deleteCurrentDataBase.askQuestion(i); //otherwise there was some internal error
                deleteCurrentDataBase.showScreen();
            }
        }
    }     
}
