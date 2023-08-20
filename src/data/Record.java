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


package data;
import java.io.*;
import javax.microedition.lcdui.Image;
import screens.Constants;

public class Record implements Comparable {
    
    protected static String[] commonLabels;   
    private static boolean[] whatToShow = Constants.SHWKEYVALUE;
    private static int whatToCompare = Constants.CMPKEY;           
    private static boolean sortAscending = true;   
    private static boolean ignoreCase = true;
    private static boolean showLabels = false;
    
    private String[] values;     //record values
    private String[] labels;     //value names
    private int recordID = -1;  //the recordID in order to manage easily the records if there is a partial modify
    private boolean modified;   //if this is false the dbmanager will not save it    
       
    public Record() {
        values = null;
        labels = null;
        modified = true;
        recordID = -1;
    }       

    public final String getValue(int i) {
        if (values == null || i >= values.length) return ""; //returning "" instead of null is important for comparing methods
        return values[i];
    }
    
    public final String getLabel (int i) {
        if (labels == null || i >= labels.length) return ""; //returning "" instead of null is important for comparing methods
        return labels[i];
    }
    
    public final void setValues(String[] value) {
        this.values = value;
        modified = true;
    }     

    public final String[] getValues() {
        return values;
    }

    public int getNumberOfValues() {
        if (values == null) return 0;
        return values.length;
    }    

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] keyNames) {
        this.labels = keyNames;
        modified = true;
    }
    
    public final int getNumberOfLabels () {
        if (labels == null) return 0;
        return labels.length;
    }
    
    public static String[] getCommonLabels() {
        return commonLabels;
    }
    
    public static String getCommonLabel (int i) {
        if (commonLabels == null || i >= commonLabels.length) return ""; //returning "" instead of null is important for comparing methods
        return commonLabels[i];
    }    

    public static final int getNumberOfCommonLabels () {
        if (commonLabels == null) return 0;
        return commonLabels.length;
    }    

    public final boolean isModified() {
        return modified;
    }

    public final void setModified(boolean modified) {
        this.modified = modified;
    }

    public final int getRecordID() {
        return recordID;
    }

    public final void setRecordID(int recordID) {
        this.recordID = recordID;
        modified = true; 
    }
    
    /** This should build a new item from its costituitive byte except for recordId and modified*/
    public final Record convertBytesToRecord(byte[] theByte) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream (theByte);
        DataInputStream dis = new DataInputStream (bais);
        String key = dis.readUTF();
        int s = dis.readInt();
        ++s;
        values = new String[s];
        values[0] = key;
        for (int i=1; i<s; ++i) values[i] = dis.readUTF();
        setValues(values); //That is important for RecordStatus
        labels = null;

        if (dis.available()>4) {//because an int is made by 4 bytes
            s = dis.readInt();
            if (s>0) {//because the crypto algorithm would add 0 pads at the end of the string
                labels = new String[s];
                for (int i=0; i<s; ++i) labels[i] = dis.readUTF();
            }
        }
        
        setModified(false);
        return this;
    }
    
    public final Record oldConvertBytesToRecord(byte[] theByte) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream (theByte);
        DataInputStream dis = new DataInputStream (bais);
        values = new String[2];
        values[0] = dis.readUTF();        
        values[1] = dis.readUTF();
        return this;
    }    
    
    /** This should convert the item in its costituitive byte except for recordId and modified */
    public final byte[] convertRecordToBytes() { 
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        DataOutputStream dos = new DataOutputStream (baos);
        try {
            dos.writeUTF(values[0]);
            if (values == null) dos.writeInt(0);
            else {
                dos.writeInt(values.length-1);
                for (int i=1; i<values.length; ++i) dos.writeUTF(values[i]);                
                if (labels == null) dos.writeInt(0);
                else {
                    dos.writeInt(labels.length);
                    for (int i=0; i<labels.length; ++i) dos.writeUTF(labels[i]);
                }
            }
        } catch (IOException ioe) {}

        byte[] theByte = baos.toByteArray();

        return theByte;
    }

    public final static void setWhatToShow(boolean[] wToString) {
        whatToShow = wToString;
    }

    public final static boolean[] getWhatToShow() {
        return whatToShow;
    }
    
    public final String toString () {
        StringBuffer result = new StringBuffer ();
        String[] lab;
        String s;
        if (labels != null) lab = labels;
        else lab = commonLabels;
        if (showLabels) 
            for (int i=0; i<whatToShow.length; ++i) {                        
                if (whatToShow[i]) {
                    if (lab != null && i < lab.length) {
                        if (i > 0) result.append ("\n");
                        result.append(lab[i]).append(Constants.SEPARATOR).append(getValue (i));                    
                    }
                }
            }
        else 
            for (int i=0; i<whatToShow.length; ++i) {                        
                if (whatToShow[i]) {
                    if (lab != null && i < lab.length) {
                        s = getValue (i);
                        if (s.length() > 0 && i > 0) result.append ('\n');                    
                        result.append(s);
                    }
                }
            }            
        return result.toString();
    }
    
    public final static Image getImage () {
        if (whatToShow == null || whatToShow.length <2) return Constants.IKEY;
        
        if (whatToShow[0]) {
            for (int i=1; i<whatToShow.length; ++i) if (whatToShow[i]) return Constants.IDB; 
            return Constants.IKEY;
        }
        return Constants.IVALUE;            

    }
    
    public final static void setWhatToCompare (int wToCompare) {
        whatToCompare = wToCompare;
    }

    public final static int getWhatToCompare() {
        return whatToCompare;
    }

    public final static void setSortAscending (boolean sAscending) {
        sortAscending = sAscending;
    }

    public final static boolean isSortAscending() {
        return sortAscending;
    }

    public final static void setIgnoreCase (boolean ignoCase) {
        ignoreCase = ignoCase;
    }

    public final static boolean isIgnoreCase() {
        return ignoreCase;
    }
    
    public final static void setStandardStatus () {
        whatToShow = Constants.SHWKEYVALUE;
        whatToCompare = Constants.CMPKEY; 
        sortAscending = true;
        ignoreCase = true;    
        showLabels = false;
    }

    public static final boolean isShowLabels() {
        return showLabels;
    }

    public static final void setShowLabels(boolean showLabels) {
        Record.showLabels = showLabels;
    }
    
    public final int compareTo (Object c) {
        Record r = (Record) c;
        String a, b;
        
        a = getValue(whatToCompare);
        b = r.getValue(whatToCompare);
        
        if (ignoreCase) {
            a = a.toLowerCase();
            b = b.toLowerCase();
        }

        if (sortAscending) return a.compareTo(b);
        return b.compareTo(a);
    }
    
    public final static String[] cloneStringArray (final String[] array) {
        String[] cloned=null;
        if (array != null) {                        
            cloned = new String[array.length];
            for (int i=0; i<array.length; ++i) cloned[i] = new String(array[i]);
        }        
        return cloned;
    }
}
