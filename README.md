# SecretCodes
A simple application to store secret codes encrypted into the phone memory

SecretCodes is a GNU software under GPL 3.0 licence. 

1st August 2010, build 38, version 1.9.2
First build on 7Th December 2003, Turin, Italy. 

Author Simone Pernice.

For every question, bug e-mail to pernice@libero.it

Encryption algorithms are AES, SHA1, PKCS12, SecureRandom (that for password generation) coming from the library: The Legion Of The Bouncy Castle (http://www.bouncycastle.org) version 1.45. 

The algorithm for automated password generation is PasswordGenerator v 1.0 code from Darron Schall and adapted by myself to J2ME with SecureRandom. Also QuickSort code comes from Internet, I was not able to find the writer to mention him here. 

I would like to thank: Janvrot Ivan Vicente Miranda who made a tool for USB synchronization over USB cable on Linux and Windows (which can be found on my web site: (http://simonepernice.freehostia.com/J2ME/SecretCodes/secretcodes.html); Jakub Stryja for its ideas on file backup and for its testing.

This program is given WITHOUT any warranty. 

SecretCodes stores encrypted databases, each of them may be used for a single topic. 

Every database is made by a set of records. 
Every record is made by a set of values and the labels associated to them. 
It is possible to backup and restore those records to/from a PC.

The initial screen shows all the available databases plus help and about options. Select a database to open it, HELP to show this help and ABOUT to read author and version details. To open a database it is also required a password because they are encrypted. 

Use EXIT button to exit the program and NEW button to create a new database providing its name and password (the password has to be inserted twice for confirmation, do not insert anything to avoid encryption).

After a database is opened list of records is showed. If the database is just created the list of labels is showed: set the number of values and their label to use for most of the records. 

Use ADD button to add records, use ADD SAME LABELS to add a new record with the same labels of the selected ones, use DELETE DATABASE button to delete the entire database, use SAVE&CLOSE button to save and close the database. 

Use SETTINGS to choose what to show of every record, what value to use to order the records, the sort order, the case sensitiveness, and if the labels should be shown. 

Use SYNCHRONIZE to backup or restore the current database in a PC or in another phone running SecretCodes: all the available connections will be showed (USB cable, IrDA and phone file system are defaults), if the phone supports JSR82 it is also possible use Bluetooth (downloading the proper version). 

The backup file in encrypted, to restore it a new database with the same password has to be created. 

Use PASSWORD to change the current password (the password has to be insert twice for confirmation, do not insert anything to avoid encryption). 

Use EDIT COMMON LABELS to set the number and name of the labels of most of the records. 

Select a record to edit its key and values. 

Use DEL RECORD to delete the record, use EDIT LABELS to set up a list of personalized number of values and value names for that record, use NEW PASSWORD to generate a random password for a value field. 

EDIT LABELS shows a new screen to add or remove values, and to use the common labels. 

GEN PASSWORD shows a new screen: select in which value will be inserted the password, the password length, if it has to include lower case chars, upper case chars, numbers, symbols or if it has to follow a template where every place is of a given type. Use GEN PASSWORD to make a new password (which can be edited), use OK to write it in the value field and CANCEL to go back.

