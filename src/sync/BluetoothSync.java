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
import java.util.*;
import java.util.Vector;
import javax.microedition.io.*;
import screens.Constants;
import screens.Strings;
import javax.obex.*;
import javax.bluetooth.*;
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
public class BluetoothSync extends Sync implements DiscoveryListener {
    
    private final static UUID[] uuidSet = {new UUID(0x1105) };       // 0x1105 is the UUID for the Object Push Profile        
    private final static int[] attrSet = {0x0100};                   // 0x0100 is the attribute for the service name element in the service record
    
    private DiscoveryAgent discoveryAgent;
    private Operation putOperation;
    private Vector remoteDevice;
    private int searchingDevice;    
    
    public BluetoothSync () {
        super ();
        remoteDevice = new Vector();
    }
    
    public void findSyncLinks() {
        remoteDevice.removeAllElements();
        searchingDevice = 0;
                        
        try {            
            discoveryAgent = LocalDevice.getLocalDevice().getDiscoveryAgent();
            discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);            
        } catch (Exception e) {
            getSyncForm().showWarning(e.getMessage());
            getSyncForm().allSyncsFound();
        } 
    }
    
    public void deviceDiscovered(RemoteDevice rd, DeviceClass deviceClass) {
        if (rd != null) remoteDevice.addElement(rd);
    }

    public void inquiryCompleted(int param) {
        if (param == DiscoveryListener.INQUIRY_COMPLETED && remoteDevice != null) {
            serviceSearcher ();
        } else {            
            getSyncForm().allSyncsFound();
        }
    }
    
    private void serviceSearcher () {
        if (searchingDevice < remoteDevice.size()) {
            try {                
                discoveryAgent.searchServices (attrSet,  uuidSet, (RemoteDevice) remoteDevice.elementAt(searchingDevice), this);                
            } catch (Exception e) {
                getSyncForm().showWarning(e.getMessage());
                getSyncForm().allSyncsFound();                
            }     
            ++ searchingDevice;
        } else {
            BluetoothSync bs = new BluetoothSync ();
            String fn="";
            try {
                fn = LocalDevice.getLocalDevice().getFriendlyName();
            } catch (Exception e) {}
                bs.setAllFields(Strings.BLUETOOTH+fn, null, false);
                getSyncForm().addSync(bs);
                getSyncForm().allSyncsFound();
        }
    }
    
    public void servicesDiscovered(int transID, ServiceRecord[] serviceRecord) {
        if (serviceRecord != null && serviceRecord.length > 0)
            try {      
                BluetoothSync bs = new BluetoothSync ();
                bs.setAllFields(Strings.BLUETOOTH+serviceRecord[0].getHostDevice().getFriendlyName(false), serviceRecord[0].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false), true);
                getSyncForm().addSync(bs);
            } catch (Exception e) {
                getSyncForm().showWarning(e.getMessage());
                getSyncForm().allSyncsFound();
            }                
    }

    public void serviceSearchCompleted(int param, int param1) {
        serviceSearcher();
    }              

    public byte[] readBytes() throws Exception {
        if (isForBackup()) throw new Exception (Strings.INTERNALERROR);                    

        BTServer btServer = new BTServer(); //blocking call
        return btServer.getReadBytes();
    }

    public void writeBytes(byte[] cryptedData) throws Exception {
        if (! isForBackup()) throw new Exception (Strings.INTERNALERROR);     

        ClientSession  cs = (ClientSession) Connector.open(getSyncLinkAddress());

        HeaderSet headerSent = cs.createHeaderSet();            

        cs.connect(headerSent);            

        Calendar c = Calendar.getInstance();
        Date d = new Date (System.currentTimeMillis());
        c.setTime(d); 

        headerSent.setHeader(HeaderSet.NAME, new StringBuffer().append(c.get(Calendar.DAY_OF_MONTH)).append('_').append(c.get(Calendar.MONTH)+1).append('_').append(c.get(Calendar.YEAR)).append('_').append(getSyncForm().getDataBase().getName()).append('_').append(Constants.SECRETCODES).toString());
        headerSent.setHeader(HeaderSet.TYPE, "text/plain");            
        headerSent.setHeader(HeaderSet.LENGTH, new Long (cryptedData.length));

        putOperation = cs.put(headerSent);

        OutputStream outputStream = putOperation.openOutputStream();           

        outputStream.write(cryptedData);
        outputStream.write('z'); //it is used to mark the end of data
        outputStream.flush();
        
        outputStream.close();
        putOperation.close();
        cs.close();                        
    }        
 
    public String getBackupMessage() {
        return Strings.BTBACKUPOPERATION;
    }

    public String getRestoreMessage() {
        return Strings.BTRESTOREOPERATION;
    }
    
 class BTServer extends ServerRequestHandler  {                
        private byte[] readBytes;
        private final UUID uuidPushProfile = new UUID(0x1105);        //UUID for Object Push Profile        
        private boolean done;
                               
        public BTServer() throws Exception {       
            done = false;
            readBytes = null;
            
            String url = "btgoep://localhost:"+uuidPushProfile+";name=FTP;authenticate=false;master=false;encrypt=false";
            
            SessionNotifier sn = (SessionNotifier) Connector.open(url);

            sn.acceptAndOpen(this);//blocking wait for the file to be sent
            
            sn.close();          
        }
        
        public int onPut(Operation putOperation) {
            readBytes=null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream ((int)putOperation.getLength());                    
                InputStream inputStream = putOperation.openInputStream();
                int b;
                while ((b = inputStream.read()) != -1 &&  b != 'z') baos.write(b);
                readBytes = baos.toByteArray();
                
                inputStream.close();
                putOperation.close();
            } catch (IOException ex) {
                done = true;
                readBytes = null;
                return ResponseCodes.OBEX_HTTP_INTERNAL_ERROR;
            }
            done = true;
            return ResponseCodes.OBEX_HTTP_OK;
        }

        public int onGet(Operation op) {
            return ResponseCodes.OBEX_HTTP_OK;
        }

        public int onConnect(HeaderSet request, HeaderSet reply) {
            return ResponseCodes.OBEX_HTTP_OK;
        }
        
        public void onDisconnect(HeaderSet request, HeaderSet reply) {
        }
        
        public byte[] getReadBytes () {
            synchronized(this) { 
                while (done == false)
                    try { 
                        Thread.sleep(1000); 
                    } catch (Throwable t) {}
            } 
            return readBytes;
        }
    }      
}
