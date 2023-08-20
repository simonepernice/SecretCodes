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
