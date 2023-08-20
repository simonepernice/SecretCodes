/*
 * PasswordGeneratorForm.java
 *
 * Created on 29 novembre 2007, 23.04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package screens;

import data.PasswordGenerator;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author simone
 */
public class PasswordGeneratorForm extends GenericForm {
    
    private ChoiceGroup cgValue, cgPasswordOption;
    private TextField tfPasswordLength, tfPasswordTemplate, tfPassword;
    private RecordForm recordForm;
    private PasswordGenerator passwordGenerator;
    
    public PasswordGeneratorForm(RecordForm recordForm) {
        super (Strings.PASSWORDGENERATOR);
        
        this.recordForm = recordForm;
        
        passwordGenerator = new PasswordGenerator ();        
        
        cgValue = new ChoiceGroup (Strings.ADDPWDTOVALUE, ChoiceGroup.EXCLUSIVE);
        cgPasswordOption = new ChoiceGroup (Strings.PWDGENOPT, ChoiceGroup.MULTIPLE);
        cgPasswordOption.append(Strings.LOWERCASE, null);
        cgPasswordOption.append(Strings.UPPERCASEE, null);
        cgPasswordOption.append(Strings.NUMBER, null);
        cgPasswordOption.append(Strings.OTHER, null);
        cgPasswordOption.append(Strings.TEMPLATE, null);
        tfPasswordLength = new TextField (Strings.PWDLENGTH, "", Constants.SPASSWORDLEN, TextField.NUMERIC);
        tfPasswordTemplate = new TextField (Strings.PWDTEMPLATE, "", Constants.SVALUE, TextField.ANY);
        tfPassword = new TextField (Strings.GENPWD, "", Constants.SVALUE, TextField.ANY);
        
        append (cgValue);
        append (tfPasswordLength);
        append (cgPasswordOption);
        append (tfPasswordTemplate);
        append (tfPassword);
        
        addCommand(Constants.OKCOMMAND);
        addCommand(Constants.GENERATEPWDCOMMAND);
        addCommand(Constants.CANCELCOMMAND);
    }
    
    public void setNumberOfValues (String[] labels) {
        cgValue.deleteAll();
        for (int i=0; i< labels.length; ++i) cgValue.append(labels[i], null);
        tfPassword.setString("");
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == Constants.CANCELCOMMAND) {
            recordForm.showScreen();
        } else if (command == Constants.OKCOMMAND) {
            if (tfPassword.getString().length() == 0) generatePassword();
            recordForm.setTFValue (cgValue.getSelectedIndex(), tfPassword.getString());
            recordForm.showScreen();
            return;
        } else { //if (c== Constants.GENERATEPWDCOMMAND)
            generatePassword();
        }        
    } 
    
    private void generatePassword () {
        passwordGenerator.clearTemplate();
        passwordGenerator.setLowercaseIncluded(true);
        passwordGenerator.setUppercaseIncluded(true);
        passwordGenerator.setNumbersIncluded(true);
        passwordGenerator.setOthersIncluded(true);

        if (tfPasswordLength.getString().length()<=0) tfPasswordLength.setString("6");
        if (Integer.parseInt(tfPasswordLength.getString()) > Constants.SVALUE) tfPasswordLength.setString(String.valueOf(Constants.SVALUE));
        passwordGenerator.setLength(Integer.parseInt(tfPasswordLength.getString()));
        tfPasswordLength.setString(String.valueOf(passwordGenerator.getLength()));

        if (!cgPasswordOption.isSelected(0)) passwordGenerator.setLowercaseIncluded(false);
        if (!cgPasswordOption.isSelected(1)) passwordGenerator.setUppercaseIncluded(false);
        if (!cgPasswordOption.isSelected(2)) passwordGenerator.setNumbersIncluded(false);
        if (!cgPasswordOption.isSelected(3)) passwordGenerator.setOthersIncluded(false);
        cgPasswordOption.setSelectedIndex(3, passwordGenerator.isOthersIncluded()); //for the user to check if all the fields were not selected
        if (cgPasswordOption.isSelected(4)) {
            passwordGenerator.setTemplate(tfPasswordTemplate.getString());
            tfPasswordTemplate.setString(passwordGenerator.getTemplate()); //for the user to check if it was actually accepted
        }
        passwordGenerator.generatePassword();
        tfPassword.setString(passwordGenerator.getPassword());        
    }
}
