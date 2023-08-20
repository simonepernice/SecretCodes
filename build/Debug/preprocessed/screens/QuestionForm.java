package screens;
/*
 * HelpScreen.java
 *
 * Created on 18 gennaio 2003, 21.48
 */
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone Pernice Stefania Giaconia
 * @version 1.0
 */
public final class QuestionForm extends GenericForm {     
    
    private int index;
    private DeletableElements showDeleteIfYes;
    private ShowableScreen showIfNo;
    private Message errorPassword;
    private TextField tfPassword;
    private String password;

    public QuestionForm(String title, ShowableScreen showIfNo, DeletableElements showDeleteIfYes) {
        this (title, showIfNo, showDeleteIfYes, null);
    }
    
    public QuestionForm(String title, ShowableScreen showIfNo, DeletableElements showDeleteIfYes, String password) {
        super (Strings.WARNING);
        
        this.showDeleteIfYes = showDeleteIfYes;
        this.showIfNo = showIfNo;
        this.password = password;
        
        errorPassword = new Message (Strings.ERROR, Strings.PASSWORWRONG, null, this);
                        
        append(title);
        
        if (password != null) {
            tfPassword = new TextField (Strings.CONFIRMPASSWORD, "", Constants.SPASSWORD, TextField.PASSWORD|TextField.ANY);
            append (tfPassword);
        }
        
        addCommand (Constants.YESCOMMAND);
        addCommand (Constants.NOCOMMAND);
    }
    
    public void askQuestion (int index) {
        this.index = index;
        if (tfPassword != null) tfPassword.setString("");
    }
    
    public final void commandAction(Command c, Displayable s) {
        if (c == Constants.NOCOMMAND) {
            showIfNo.showScreen ();
        } else {//if (c == Strings.YESCOMMAND
            if (tfPassword == null || tfPassword.getString().equals(password)) {
                showDeleteIfYes.deleteElementAt(index);
                showDeleteIfYes.showScreen();
            } else errorPassword.showScreen();
        }
    }      
}
