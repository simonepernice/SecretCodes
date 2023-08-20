package screens;
/*
 * Strings.java
 *
 * Created on 14 gennaio 2003, 22.25
 */

/**
 *
 * @author  Simone Pernice Stefania Giaconia
 * @version 1.1
 *
 * Added ICLEANALL icon for the clean all functin
 */
public final class Strings {
    public static final String KEYLABEL = "Key Label";
    public static final String SECRETNOTE  = "Secret Codes";
    public static final String SYNCHRONIZE = "Synchronize";
    public static final String DBOPEN  = "Open Database";
    public static final String NAME  = "Name";
    public static final String PASSWORD  = "Password";
    public static final String DELDB  = "Delete Database";
    public static final String CHANGEPASSWORD  = "Change password";
    public static final String SETUP = "View Options";
    public static final String WARNING= "Warning";
    public static final String UNABLECREATEDB = "It is not possible to create the required database. Its name may exists or the memory may be full.";
    public static final String UNABLELOADDB = "It is not possible to load the required database. Its name may not exist.";        
    public static final String UNABLEDELETE = "It is not possible to delete the required database.";
    public static final String UNABLEREAD = "It is not possible to read the database. Probably the inserted password is wrong or the database was corrupted.";
    public static final String HELP= "Help";    
    public static final String HELPINSTRUCTION= "This program is given WITHOUT any warranty. SecretCodes stores encrypted databases, each of them may be used for a single topic. Every database is made by a set of records. Every record is made by a set of values and the labels associated to them. It is possible to backup and restore those records to/from a PC.\nThe initial screen shows all the available databases plus help and about options. Select a database to open it, HELP to show this help and ABOUT to read author and version details. To open a database it is also required a password because they are encrypted. Use EXIT button to exit the program and NEW button to create a new database providing its name and password (the password has to be inserted twice for confirmation, do not insert anything to avoid encryption).\nAfter a database is opened list of records is showed. If the database is just created the list of labels is showed: set the number of values and their label to use for most of the records. Use ADD button to add records, use ADD SAME LABELS to add a new record with the same labels of the selected ones, use DELETE DATABASE button to delete the entire database, use SAVE&CLOSE button to save and close the database. Use SETTINGS to choose what to show of every record, what value to use to order the records, the sort order, the case sensitiveness, and if the labels should be shown. Use SYNCHRONIZE to backup or restore the current database in a PC or in another phone running SecretCodes: all the available connections will be showed (USB cable, IrDA and phone file system are defaults), if the phone supports JSR82 it is also possible use Bluetooth (downloading the proper version). The backup file in encrypted, to restore it a new database with the same password has to be created. Use PASSWORD to change the current password (the password has to be insert twice for confirmation, do not insert anything to avoid encryption). Use EDIT COMMON LABELS to set the number and name of the labels of most of the records. Select a record to edit its key and values. Use DEL RECORD to delete the record, use EDIT LABELS to set up a list of personalized number of values and value names for that record, use NEW PASSWORD to generate a random password for a value field. EDIT LABELS shows a new screen to add or remove values, and to use the common labels. GEN PASSWORD shows a new screen: select in which value will be inserted the password, the password length, if it has to include lower case chars, upper case chars, numbers, symbols or if it has to follow a template where every place is of a given type. Use GEN PASSWORD to make a new password (which can be edited), use OK to write it in the value field and CANCEL to go back.";    
    public static final String ABOUT = "About";
    public static final String ABOUTINFO = "SecretCodes is a GNU software under GPL software. 1st August 2010, build 38, version 1.9.2, first build on 7Th December 2003, Turin, Italy. Author Simone Pernice.\nFor every question, bug e-mail to pernice@libero.it\nEncryption algorithms are AES, SHA1, PKCS12, SecureRandom (that for password generation) coming from the library: The Legion Of The Bouncy Castle (http://www.bouncycastle.org) version 1.45. The algorithm for automated password generation is PasswordGenerator v 1.0 code from Darron Schall and adapted by myself to J2ME with SecureRandom. Also QuickSort code comes from Internet, I was not able to find the writer to mention him here. I would like to thank: Janvrot Ivan Vicente Miranda who made a tool for USB synchronization over USB cable on Linux and Windows (which can be found on my web site: (http://simonepernice.freehostia.com/J2ME/SecretCodes/secretcodes.html); Jakub Stryja for its ideas on file backup and for its testing.";
    public static final String RECORD = "Record Data";
    public static final String LABELFORM = "Record Labels";
    public static final String COMMONLABELFORM = "Common Labels";
    public static final String KEY = "Key";
    public static final String LABEL = " Label";
    public static final String VALUE = "Value ";
    public static final String UNREADABLE = "UNREADABLE";
    public static final String DELETE = "Delete";
    public static final String SAVE = "Save";
    public static final String NOESAVEABLE = "It was not possible saving the database. For example because the memory is full.";
    public static final String KEYREQUIRED = "For every record you must provide at least a key.";
    public static final String ATLEAST2VALUESREQ = "At lease two fields are required.";
    public static final String CONFIRMDATA = "Insert data";
    public static final String RECORDLIST = "List of records";
    public static final String INSERTPASSWORD = "Insert a new password";
    public static final String NOTCYPHERED = "If you do not insert a password the database is stored, backup and restored WITHOUT any encryption. Do not to insert password also to re-open it later. It is possible add a password using password option.";
    public static final String SUREDELETERECORD = "Are you sure to delete this record?\n\nYour record will be LOST!";
    public static final String SUREDELETELABEL  = "Are you sure to delete last label?\n\nIts value(s) may be LOST.";
    public static final String SURECOMMONLABEL  = "Are you sure to use the common labels instead of your personalize ones?\n\nYour labels will be LOST.";
    public static final String SUREDELETEDB = "Are you sure this database?\n\nALL your records will be LOST!";
    public static final String CABLEBACKUPOPERATION = "Now link the phone to a PC through an USB cable. On a PC start a terminal emulation program on the phone COM port. Set the terminal program to save on a file the incoming text. Push a character on the PC keyboard to get phone backup in text form. Remember the password for future restore!";
    public static final String CABLERESTOREOPERATION = "Now link the phone to a PC through an USB cable. On a PC start a terminal emulation program on the phone COM port. Send the backup text file previously saved by backup option to add all those records to the current database.";
    public static final String BTBACKUPOPERATION = "SecretCodes will send a file to the selected device, accept and save it. Remember the password for future restore!";
    public static final String BTRESTOREOPERATION = "Send a previous saved backup to the phone with Bluetooth connection. The records will be added to the current database. Both databases must have the same password.";    
    public static final String FSBACKUPOPERATION = "SecretCodes will save a file in the internal phone file system with the contents of the current database. The file name is like the current database with scbk extension. If a file with the same name is already present, it will be deleted! Remember the password for future restore!";
    public static final String FSRESTOREOPERATION = "SecretCodes will load the backup file chosen among the ones saved in the phone file system. The records will be added to the current database. Both databases must have the same password!";    
    public static final String NOPORTFOUND = "It was not possible to found a port for serial connection.";
    public static final String BACKUP = "Backup via ";
    public static final String RESTORE = "Restore via ";
    public static final String PORTS = "Select Action:";
    public static final String ACTION = "To restore a backup: make a new database with the same password of the backup then use restore option.";
    public static final String SHOWRECORDS = "What show from records:";
    public static final String SHOW = "Show";
    public static final String HIDE = "Hide";
    public static final String LABELS = "Labels:";
    public static final String ORDER = "Order records by:";
    public static final String SORT = "Sort records:";
    public static final String ASCENDING = "from A to Z";
    public static final String DESCENDING =  "from Z to A";
    public static final String CASE = "String Case";
    public static final String IGNORE = "Ignore";           
    public static final String CONSIDER = "Consider";           
    public static final String OLDVERSION = "The database loaded was made with a version of Secret Notes before 1.7.0. It used an easy-to-brake cryptographic algorithm. When you will close the database it will be saved using the new encryption algorithms to improve security. Please make a new backup of the database since restore from old version backup is not possible.";
    public static final String NEWCREATION  = "When a new database is created you need to define the list of labels to be used for most of the records.";
    public static final String CONFIRMPASSWORD = "Confirm Password";
    public static final String PASSWORDSERROR = "The entered passwords do not match.";        
    public static final String PASSWORWRONG = "The entered password is wrong.";            
    public static final String SERIAL = "cable ";    
    public static final String ERROR = "Error";
    public static final String BLUETOOTH = "bluetooth ";
    public static final String WAIT = "Wait";
    public static final String WAITFORLINKSCOMPLETION = "Wait until all the available connections are found...";
    public static final String PASSWORDGENERATOR = "Password Generator";
    public static final String PWDGENOPT = "Generated password will include";
    public static final String ADDPWDTOVALUE = "Insert password into ";
    public static final String LOWERCASE = "Lower case chars";
    public static final String UPPERCASEE = "Upper case chars";
    public static final String NUMBER = "Numbers";
    public static final String OTHER = "Other symbols";
    public static final String TEMPLATE = "Use the following template";
    public static final String PWDLENGTH = "Password length";
    public static final String PWDTEMPLATE = "Make a template with following chars l=lowerCaseChar, u=upperCaseChar, n=number, o=other";
    public static final String GENPWD = "Generated Password";
    public static final String INTERNALERROR = "Internal Error. Please send a detailed description of the steps to get this error to pernice@libero.it";
        
    public static final String NEWCOMMANDSTR = "NEW";
    public static final String EXITCOMMANDSTR = "EXIT";
    public static final String DELETECOMMANDSTR = "DEL RECORD";
    public static final String DELETEDBCOMMANDSTR = "DEL DATABASE";
    public static final String OKCOMMANDSTR = "OK";
    public static final String ADDCOMMANDSTR = "ADD";
    public static final String CLONECOMMANDSTR = "ADD SAME LABELS";
    public static final String CREATECOMMANDSTR = "CREATE";
    public static final String CLOSECOMMANDSTR = "SAVE&CLOSE";
    public static final String OPENCOMMANDSTR = "OPEN";
    public static final String NOCOMMANDSTR = "NO";
    public static final String YESCOMMANDSTR = "YES";
    public static final String SYNCCOMMANDSTR = "SYNCHRONIZE";
    public static final String PASSWORDCOMMANDSTR = "PASSWORD";    
    public static final String ADDLABELCOMMANDSTR = "APPEND NEW LABEL";    
    public static final String DELLABELCOMMANDSTR = "REMOVE LAST LABEL";  
    public static final String COMLABCOMMANDSTR = "USE COMMON LABELS";  
    public static final String EDITLABELCOMMANDSTR = "EDIT LABELS";
    public static final String EDITCOMLABELCOMMANDSTR = "EDIT COMMON LABELS";
    public static final String SETUPCOMMANDSTR = "SETTINGS";
    public static final String CANCELCOMMANDSTR = "CANCEL";  
    public static final String GENERATECOMMANDSTR = "NEW PASSWORD";
    public static final String FILERESTORE = "phone file ";
    public static final String FILEBACKUP = "phone filesystem";
}
