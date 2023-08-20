package data;
/*
 * Database.java
 *
 * Created on 6 dicembre 2003, 23.46
 */
import java.util.Vector;
import java.io.*;
import javax.microedition.rms.*;
import org.bouncycastle.util.encoders.Hex;
import screens.Strings;

/**
 *
 * @author  Simone
 *
 * Respect to Vector it has a password, a name and can work only with Record
 * All the insert function are overloaded to check the correct type (Record)
 * It is added a new method to get directly a record (without have to casting)
 */
public final class DataBase {
    private String name;                        //database name 
    private String password;                    //0 for not cyphering
    private boolean changed;                    //If the db does not change nothin will be stored in the database
    private boolean passwordChanged;            //If the passwordchenges the db has to be resaved
    private boolean unreadable;                 //If some  database's record is not readable something could be wrong with the password
    private boolean oldVersion;                 //to remember if the database was an old version    
    private RecordStore myRS;                   //Where the data are stored
    private Vector deletedRecords;              //list of Integer IDs of all deleted records
    private Vector records;                     //list of the contained records
    private RecordsStatus recordsStatus;        //the record to keep trace of the latest records status
    
    private DataBase(String name, String password) {
        this (name, password, 10);
    }
    
    private DataBase(String name, String password, int nRecords) {
        this.name = name;
        this.password = password;
        unreadable = false;
        changed = false;
        oldVersion = false;
        passwordChanged = false;
        myRS = null;
        deletedRecords = new Vector (1+nRecords/10);
        records = new Vector (nRecords);
        recordsStatus = new RecordsStatus (2);
    }    

    public boolean isOldVersion() {
        return oldVersion;
    }

    public String getName() {
        return name;
    }    
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
        passwordChanged = changed = true;
    }   
    
    public boolean isUnReadable () {
        return unreadable;
    }

    public void sortRecords() {
        QuickSort.sort (records);
    }
    
    public int addRecord(Record record) {
         changed = true;
         int i, s = records.size();
         for (i = 0; i < s; ++i) {
             if (record.compareTo(getRecordAt(i)) < 0) {
                 records.insertElementAt(record, i);
                 break;
             }             
         }
         if (i == s) records.addElement(record);
         return i;
    }       
    
     public int setRecordtAt (Record rec, int index) {
         changed = true;
         records.removeElementAt(index);
         return addRecord (rec);
     }      
     
     public void removeRecordAt(int index) {         
         changed = true;
         deletedRecords.addElement(new Integer (getRecordAt(index).getRecordID()));
         records.removeElementAt(index);
     }     
         
     public Record getRecordAt (int i) {
         return (Record) records.elementAt(i);
     }
     
     public int size () {
         return records.size();
     }

    public RecordsStatus getRecordsStatus() {
        return recordsStatus;
    }          
     
     public static ComparableString[] dataBaseList () {
        String[] lrs = RecordStore.listRecordStores();
        if (lrs == null) return null;
        ComparableString[] clrs = new ComparableString[lrs.length];
        for (int i=0; i<lrs.length; ++i) clrs[i] = new ComparableString(lrs[i]);
        QuickSort.sort(clrs) ;
        return clrs;
    }
    
    public static DataBase createDataBase (String name, String password) {
        RecordStore rs;
        String[] ldb = RecordStore.listRecordStores() ;        
        if (ldb != null) for (int i = 0; i < ldb.length; ++i) if (ldb[i].equals(name)) return null;   //If the name exists the rutine returns null        
        try {
            rs = RecordStore.openRecordStore(name, true);
        } catch (RecordStoreException rse) {
            return null; //something goes wrong in creating the database
        }
        DataBase db = new DataBase(name, password);
        db.myRS = rs;
        db.changed = true;
        db.passwordChanged = true;        
        return db;
    }
    
    public static DataBase openAndLoadDataBase (String name, String password) {
        RecordStore rs=null;
        DataBase db;        
        try {
            rs = RecordStore.openRecordStore(name, false);  
            if (rs == null) return null; //the record name does not exist
//#mdebug
            System.out.println("Opened Record Store "+name);
//#enddebug             
            db = new DataBase(name, password, rs.getNumRecords());
            db.myRS = rs;     
            Cypher cypher = new Cypher(password);
            OldCypher oldCypher = null;
            RecordEnumeration re = rs.enumerateRecords(null, null, false);
            Record c; 
            int id;          
            while (re.hasNextElement()) {
                c = new Record();
                id = re.nextRecordId(); 
                try { 
                    if (db.oldVersion) c.oldConvertBytesToRecord(oldCypher.unCrypte(rs.getRecord(id))); 
                    else c.convertBytesToRecord(cypher.unCrypte(rs.getRecord(id)));                      
                } catch (Exception e) {
                    try {
                        int i;
                        for (i=0; i<password.length(); ++i) if (! Character.isDigit(password.charAt(i))) break;
                        if (i==password.length() && password.length()<=6) {
                            oldCypher = new OldCypher (Long.parseLong(password));
                            c.oldConvertBytesToRecord(oldCypher.unCrypte(rs.getRecord(id)));  //try if it was an old versione
                            db.oldVersion = true; //if no exceptio, it is an OldDB
                        } else {
                            db.unreadable = true;
                            break;
                        }
                    } catch (Exception ioe) {
                        db.unreadable = true;
                        break;
                    }
                }               
                c.setRecordID (id);
                c.setModified(false);
//#mdebug
                System.out.println("Loaded record "+c);
//#enddebug                
                if (RecordsStatus.isARecordStatusKey(c.getValue(0))) {
                    if (RecordsStatus.isLatestRecordStatusKey (c.getValue(0))) {
                        db.recordsStatus = new RecordsStatus (c);
                        db.recordsStatus.writedRecordsStatus();
                    } else db.recordsStatus = null; //so that the records status can be made as required
                }  else db.addRecord(c);
            }
            re.destroy();        
        } catch (RecordStoreException rse) {
            try {
                if (rs != null) rs.closeRecordStore();
            } catch (RecordStoreException rse2) {}
            return null;
        }        
        
        try {
            if (db.unreadable) {
                rs.closeRecordStore();
                return db;
            }            
        } catch (RecordStoreException rse3) {}
        
        if (db.oldVersion) db.changed = db.passwordChanged = true;
        else db.changed = db.passwordChanged = false;    
        
        if (db.recordsStatus == null) {
            final int s = db.size();
            int i, l, lbls=0;
            for (i=0; i<s; ++i) if ((l=db.getRecordAt(i).getNumberOfValues()) > lbls) lbls = l;
            db.recordsStatus = new RecordsStatus(lbls);
            db.changed = true;
        }
        
        db.sortRecords();
        
        return db;
    }
    
    public static boolean deleteDataBase (String name) {
        try {
            RecordStore.deleteRecordStore(name);
        } catch (RecordStoreException rse) {
//#mdebug
            System.out.println ("Impossible to delete "+name+" because of "+rse.getMessage());
//#enddebug
            return false;
        }
        return true;
    }
    
    public boolean saveAndCloseDataBase () {
        if (myRS == null) return false;
        try {           
            if (unreadable) {
                myRS.closeRecordStore();
                return false;
            }
            
            Cypher cypher = new Cypher (password);            
            recordsStatus.readRecordsStatus();
            addSetRecordInRecordStore(recordsStatus, cypher);
                
            if (!changed) {
//#mdebug
                System.out.println ("Closing without editing");
//#enddebug
                myRS.closeRecordStore();
                return true;
            }
            
            int i, id, end = deletedRecords.size();
            for (i=0; i < end; ++i) {
                id = ((Integer) deletedRecords.elementAt(i)).intValue();
//#mdebug
                    System.out.println ("Deleted Record\n"+deletedRecords.elementAt(i));
//#enddebug                                    
                if (id != -1) {
                    myRS.deleteRecord(id);
//#mdebug
                    System.out.println ("Deletion from storage");
//#enddebug                    
                }
            }
            
            end = records.size();            
            Record tmp;            
            for (i=0; i<end; ++i) {                        
                tmp = this.getRecordAt(i); 
                addSetRecordInRecordStore (tmp, cypher);
            }
            myRS.closeRecordStore();
//#mdebug
            System.out.println ("Closed record store");
//#enddebug
            return true;
        } catch (RecordStoreException rse) {
            return false;
        }            
    } 
    
    private void addSetRecordInRecordStore (Record tmp, Cypher cypher) throws RecordStoreException {
        if (passwordChanged || tmp.isModified()) {  
            int id;
            byte[] data;
            id = tmp.getRecordID();

            data = tmp.convertRecordToBytes();
            data = cypher.crypte (data);                    

            if (id == -1) { 
                myRS.addRecord(data, 0, data.length);   
//#mdebug
                System.out.println ("Added record on db\n"+tmp);
//#enddebug
            } else {
                myRS.setRecord(id, data, 0, data.length); 
//#mdebug
                System.out.println ("Modified record on db\n"+tmp);
//#enddebug
            }
        }        
    }     
    
    public void restoreDataBase (byte[] cryptedData) throws Exception {
        try {                 
            if (cryptedData == null) throw new Exception (Strings.INTERNALERROR); //Internal error

            Cypher cypher = new Cypher (password);

            DataInputStream dis = new DataInputStream (new ByteArrayInputStream (cypher.unCrypte(Hex.decode(cryptedData))));

            final int size = dis.readInt();
            int i, length;
            Record c;
            byte[] data = null;
            for (i=0; i < size; ++i) {
                length = dis.readInt();
                if (data == null || data.length < length) data = new byte[length];
                dis.read(data, 0, length);
                c = new Record ();
                c.convertBytesToRecord(data);
                c.setModified(true); //this is required to store new records when restoring a not-empty database
                if (RecordsStatus.isARecordStatusKey(c.getValue(0))) {
                    int oldid = recordsStatus.getRecordID();
                    recordsStatus = new RecordsStatus (c);
                    recordsStatus.setRecordID(oldid); //it is very important to set the old id, otherwise a new records status would be created in the database. Database can work with two records status. However when the password will be changed, the old records status will be stored on a different password than the other records making the database unreadable
                    recordsStatus.writedRecordsStatus();
                } else addRecord (c);
            }
        } catch (Exception e) {
            throw new Exception (Strings.UNABLEREAD);            
        }
    }      
       
    public byte[] backupDataBase () throws Exception {                                                                                                        	                                                      
        ByteArrayOutputStream baos = new ByteArrayOutputStream (myRS == null ? records.size()*30 : myRS.getSize());
        DataOutputStream dos = new DataOutputStream (baos);

        int i;
        final int end = records.size();
        Record tmp;
        byte[] data;

        dos.writeInt(end+1);

        recordsStatus.readRecordsStatus(); //get the picture of the current status, moreover if just loaded from old version it would rise an exception
        data = recordsStatus.convertRecordToBytes();
        dos.writeInt(data.length);
        dos.write(data);

        for (i=0; i<end; ++i) {  
            tmp = getRecordAt(i); 
            data = tmp.convertRecordToBytes();
            dos.writeInt(data.length);
            dos.write(data);            
        }                

        Cypher cypher = new Cypher (password);

        byte[] cryptedData = cypher.crypte(baos.toByteArray());
        
        //#mdebug
                    System.out.println();
                    for (i=0; i<cryptedData.length; ++i) System.out.print((char)cryptedData[i]);
                    System.out.println();
        //#enddebug          
        
        return Hex.encode(cryptedData);
    }    
}
