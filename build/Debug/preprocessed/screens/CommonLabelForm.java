package screens;
/*
 * HelpScreen.java
 *
 * Created on 18 gennaio 2003, 21.48
 */
import data.Record;
import javax.microedition.lcdui.*;

/**
 *
 * @author  Simone Pernice Stefania Giaconia
 * @version 1.0
 */
public final class CommonLabelForm extends GenericLabelForm {     
        
    /** Creates a new instance of HelpScreen */
    public CommonLabelForm(RecordsList recordsList) {
        super (Strings.COMMONLABELFORM, Constants.SLABEL, recordsList);
    }    

    public void OKAction() {
        record.setLabels(getTFValueStrings());
        ((RecordsList) back).rebuildList();      
    }


    protected String getValueAt(int i) {
        return Record.getCommonLabel(i);
    }   
}
