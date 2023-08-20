/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sync;

import java.io.*;
import javax.microedition.io.file.*;
import javax.microedition.io.Connector;
import java.util.Enumeration;
import screens.Constants;
import screens.Strings;

/**
 *
 * @author simone
 */
public class FileSync extends Sync {

    public void findSyncLinks() {
        if (System.getProperty("microedition.io.file.FileConnection.version") == null) {
            getSyncForm().allSyncsFound();
            return;
        }
        
        String sRoot=null;
        sRoot = System.getProperty("fileconn.dir.memorycard");

        if (sRoot == null) {

            Enumeration eRoots;
            try {
                eRoots = FileSystemRegistry.listRoots();
            } catch (SecurityException se) {
                getSyncForm().allSyncsFound();
                return;
            }

            //This looks in all the file systems, for every one it verify it is readable/writable and if it contains the word card (preferred because it is removable)

            while (eRoots != null && eRoots.hasMoreElements()) {
                FileConnection test=null;
                sRoot = "file:///" + (String) eRoots.nextElement();
                try {
                    test = (FileConnection) Connector.open(sRoot);
                } catch (Throwable ioe) {
                    sRoot = null;
                    continue;
                }

                //if (!(test.canRead() && test.canWrite())) sRoot = null; //skip if it is not readable or writable
                //else if (sRoot.toLowerCase().indexOf("card") != -1) break; //stop if it is a memory card

                //Patch from Laszlo
                if (!(test.canRead())) sRoot = null; //skip if it is not readable or writable
                else if (sRoot.toLowerCase().indexOf("card") != -1 || sRoot.toLowerCase().indexOf("mmc") != -1 ) break; //stop if it is a memory card

            }

        }

        if (sRoot == null) {
            getSyncForm().allSyncsFound();
            return;
        }
        
//        sRoot += getSyncForm().getDataBase().getName()+".scbk";
        
        FileConnection fcBackupRestore;  
        try {
            fcBackupRestore = (FileConnection) Connector.open(sRoot, Connector.READ);
        } catch (Throwable itoe) {    
            getSyncForm().allSyncsFound();
            return;
        }
        
        Enumeration fileNames;
        try {
            fileNames = fcBackupRestore.list();
        } catch (Throwable t) {
            getSyncForm().allSyncsFound();
            return;
        }
        
        String fn;
        int i;
        while (fileNames != null && fileNames.hasMoreElements()) {
            fn = (String) fileNames.nextElement();
            i = fn.indexOf(Constants.SCEXT);
            if (i != -1) {
                FileSync fs = new FileSync ();
                fs.setAllFields(Strings.FILERESTORE+fn.substring(0,i), sRoot+fn, false);
                getSyncForm().addSync (fs);                  
            }
        }
        
        FileSync fs = new FileSync ();
        fs.setAllFields(Strings.FILEBACKUP, sRoot+getSyncForm().getDataBase().getName()+Constants.SCEXT, true);
        getSyncForm().addSync (fs);            
        
        try {
            fcBackupRestore.close();
        } catch (Throwable ioe) {
        }
        
        getSyncForm().allSyncsFound();
    }

    public byte[] readBytes() throws Exception {
        FileConnection fcBackupRestore;  
        fcBackupRestore = (FileConnection) Connector.open(getSyncLinkAddress(), Connector.READ);
        if (! fcBackupRestore.exists()) {
            fcBackupRestore.close();
            throw new Exception ();
        }
        InputStream is = fcBackupRestore.openDataInputStream();
        byte[] result = new byte[(int)fcBackupRestore.fileSize()];
        is.read(result);
        is.close();
        fcBackupRestore.close();        
        return result;
    }

    public void writeBytes(byte[] cryptedData) throws Exception {
        FileConnection fcBackupRestore;  
        fcBackupRestore = (FileConnection) Connector.open(getSyncLinkAddress(), Connector.READ_WRITE);
        if (fcBackupRestore.exists()) {
            fcBackupRestore.delete();
        }
        fcBackupRestore.create();
        OutputStream os = fcBackupRestore.openDataOutputStream();
        os.write(cryptedData);
        os.flush();
        os.close();
        fcBackupRestore.close();
    }

    public String getBackupMessage() {
        return Strings.FSBACKUPOPERATION;
    }

    public String getRestoreMessage() {
        return Strings.FSRESTOREOPERATION;
    }

}
