package screens;

import data.DataBase;
import java.util.Vector;
import javax.microedition.lcdui.*;
import sync.*;

/*
 * SyncForm.java
 *
 * Created on 8 maggio 2007, 18.04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public final class SyncForm extends GenericForm implements Runnable {
       
    private ChoiceGroup cgPorts;
    private Message mWarning, mBackup, mRestore, mWait;
    private RecordsList recordsList;    
    private int ctf;
    private boolean lookForSyncs;

    private Vector vSync;
    
    public SyncForm(RecordsList recordList) {
        super (Strings.SYNCHRONIZE);        
        
        this.recordsList = recordList;
        vSync = new Vector ();

        mWarning = new Message (Strings.WARNING, null, null, this); 
        mBackup = new Message (Strings.BACKUP);
        mRestore = new Message (Strings.RESTORE);
        mWait = new Message (Strings.WAIT, Strings.WAITFORLINKSCOMPLETION);
                
        cgPorts = new ChoiceGroup (Strings.PORTS, ChoiceGroup.EXCLUSIVE);
        cgPorts.setFitPolicy(Choice.TEXT_WRAP_ON);
        
        append (Strings.ACTION);
        append (cgPorts);
        
        addCommand(Constants.CANCELCOMMAND);        
        addCommand(Constants.SYNCCOMMAND);               
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == Constants.CANCELCOMMAND) recordsList.showScreen ();
        else new Thread (this).start();
    }
    
    public void showWarning (String message) {
        mWarning.setMessage(message);
        mWarning.showScreen();       
    }

    public void addSync (Sync sync) {
        vSync.addElement (sync);
        cgPorts.append (sync.toString(), null);
    }   
    
    public void allSyncsFound() {
        synchronized (this) {
            --ctf;
        }     
        if (ctf == 0) showScreen();
    }
    
    public void addSyncWays () {
        mWait.showScreen();
        
        lookForSyncs = true;
        new Thread (this).start();         
    }

    public void run() {       
        if (lookForSyncs) {
            lookForSyncs = false;
//#if Debug
//#            ctf = 3; 
//#elif DefaultConfiguration
//#            ctf = 1;
//#else 
           ctf = 2;            
//#endif

            vSync.removeAllElements();
            cgPorts.deleteAll();

            Sync.setSyncForm(this); //this is required for multiple SyncForm are opened

            new SerialSync().findSyncLinks();
//#if Debug
//#             new FileSync().findSyncLinks(); 
//#             new BluetoothSync().findSyncLinks();
//#elif Bluetooth
            new BluetoothSync().findSyncLinks();    
//#elif FileSystem
//#             new FileSync().findSyncLinks();            
//#endif                              
        } else {
            Sync s = (Sync) vSync.elementAt(cgPorts.getSelectedIndex());
            if (s.isForBackup()) {
                mBackup.setMessage(s.getBackupMessage());
                mBackup.showScreen();
                
                try {
//#if Debug     
//#                     recordsList.getDataBase().backupDataBase();
//#else         
                s.writeBytes(recordsList.getDataBase().backupDataBase());
//#endif
                    recordsList.showScreen();
                } catch (Exception e) {
                    showWarning(e.getMessage());
                }                                     
            } else { //if (cgToDo.getSelectedIndex() == 1) { //restoreDataBase
                mRestore.setMessage(s.getRestoreMessage());
                mRestore.showScreen();                
                try {
//#if Debug
//#                     recordsList.getDataBase().restoreDataBase("write here the string of your database to decypher".getBytes());
//#else
                recordsList.getDataBase().restoreDataBase(s.readBytes());
//#endif
                    recordsList.rebuildList();
                    recordsList.showScreen();
                } catch (Exception e) {
                    showWarning (e.getMessage());
                }
            }   
        }
    }

    public DataBase getDataBase() {
        return recordsList.getDataBase();
    }
}
