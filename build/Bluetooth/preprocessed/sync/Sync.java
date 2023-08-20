package sync;
import java.io.*;
import javax.microedition.io.*;
import screens.Strings;
import screens.SyncForm;

/*
 * Sync.java
 *
 * Created on 20 novembre 2007, 22.41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public abstract class Sync {
    private static SyncForm syncForm;
    
    private String friendlyName;
    private String connectionAddress;
    private boolean forBackup;

    public static void setSyncForm(SyncForm sForm) {
        syncForm = sForm;
    }
    
    public static SyncForm getSyncForm() {
        return syncForm;
    }      

    public void setAllFields (String friendlyName, String connectionAddress, boolean forBackup) {
        this.friendlyName = friendlyName;
        this.connectionAddress = connectionAddress;
        this.forBackup = forBackup;
    }

    public String getSyncLinkAddress() {
        return connectionAddress;
    }

    public String toString () {
        if (forBackup) return Strings.BACKUP+friendlyName;
        else return Strings.RESTORE+friendlyName;
    }

    public boolean isForBackup() {
        return forBackup;
    }

    public abstract void findSyncLinks ();
    
    public abstract byte[] readBytes () throws Exception;    
    
    public abstract void writeBytes (byte[] cryptedData) throws Exception;
    
    public abstract String getBackupMessage ();
    
    public abstract String getRestoreMessage ();    
}
