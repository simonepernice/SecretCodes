package screens;
/*
 * PasswordForm.java
 *
 * Created on 8 dicembre 2003, 10.55
 */
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone
 */
public final class PasswordForm extends GenericForm {
    
    private TextField tfNewpassword, tfNewpasswordc;
    private RecordsList recordsList;
    private Message errorPassword;
    
    /** Creates a new instance of PasswordForm */
    public PasswordForm(RecordsList recordsList) {
        super (Strings.INSERTPASSWORD);

        this.recordsList = recordsList;
        
        errorPassword = new Message (Strings.WARNING, Strings.PASSWORDSERROR, null, this);
        
        tfNewpassword = new TextField (Strings.PASSWORD, null, Constants.SPASSWORD, TextField.PASSWORD|TextField.ANY);
        tfNewpasswordc = new TextField (Strings.CONFIRMPASSWORD, null, Constants.SPASSWORD, TextField.PASSWORD|TextField.ANY);
        
        append(tfNewpassword);
        append(tfNewpasswordc);
        
        addCommand(Constants.OKCOMMAND);
        addCommand(Constants.CANCELCOMMAND);
    }
    
    /** This set the object as current display. A message is given in order to undersand what view.
     *  It is optionally given the index and the objet changed to make easier for the actived window
     *  to reflect the modification
     */
    public void setPassword () {
        changed = false;
        tfNewpassword.setString("");
        tfNewpasswordc.setString("");        
    }
    
    public final void commandAction(Command c, Displayable s) { 
        if (c == Constants.CANCELCOMMAND) {
            recordsList.showScreen();
        } else { //if (c == Constants.OKCOMMAND)
            if (!tfNewpassword.getString().equals(tfNewpasswordc.getString())) {
                errorPassword.showScreen();
            } else {
                if (changed && ! tfNewpassword.getString().equals(recordsList.getDataBase().getPassword())) {            
                    recordsList.getDataBase().setPassword(tfNewpassword.getString());
                    if (tfNewpassword.getString().length() == 0) recordsList.noCyphering();
                }
                recordsList.showScreen ();        
            }
        }
    }
}
