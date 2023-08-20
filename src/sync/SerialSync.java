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
/*
 * SerialSync.java
 *
 * Created on 21 novembre 2007, 21.23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simone
 */
public class SerialSync extends Sync {
    public void findSyncLinks() {
        String p, ports = System.getProperty("microedition.commports");

        if (ports == null) {
            getSyncForm().allSyncsFound();
            return;
        }
        
        int c;
        SerialSync ss;
        do  {
            c = ports.indexOf(',');
            if (c == -1) c = ports.length();
            p = ports.substring(0, c);
            ss = new SerialSync ();
            ss.setAllFields (Strings.SERIAL+p, p, true);
            getSyncForm().addSync (ss);
            ss = new SerialSync ();            
            ss.setAllFields (Strings.SERIAL+p, p, false);
            getSyncForm().addSync (ss);            
            if (c<ports.length()) ++c; //skip comma
            ports = ports.substring(c);
        } while (ports.length() > 0);    
        
        getSyncForm().allSyncsFound();
    }

    public byte[] readBytes() throws Exception {
        if (isForBackup()) throw new Exception (Strings.INTERNALERROR);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();        
                     
        StreamConnection sc = (StreamConnection) Connector.open("comm:"+getSyncLinkAddress());           
        InputStream inputStream = sc.openInputStream();

        int b;
        while ((b = inputStream.read()) != -1 && b != 'z') baos.write(b);

        inputStream.close();
        sc.close();
               
        return baos.toByteArray();
    }

    public void writeBytes(byte[] cryptedData) throws Exception{
        if (! isForBackup()) throw new Exception (Strings.INTERNALERROR);
          
        StreamConnection sc = (StreamConnection) Connector.open("comm:"+getSyncLinkAddress());
        OutputStream outputStream = sc.openOutputStream();

        //Wait for the user to push a key
        InputStream inputStream = sc.openInputStream();
        inputStream.read();//blocking  !!!                      
        outputStream.write(cryptedData);
        outputStream.write('z'); //it is used to mark the end of data
        outputStream.flush();

        inputStream.close();
        outputStream.close();
        sc.close();
    }

    public String getBackupMessage() {
        return Strings.CABLEBACKUPOPERATION;
    }

    public String getRestoreMessage() {
        return Strings.CABLERESTOREOPERATION;
    }        
}
