package data;
/*
 * RecordsStatus.java
 *
 * Created on 17 novembre 2007, 23.25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import screens.Constants;
import screens.Strings;
/**
 *
 * @author simone
 */
public final class RecordsStatus extends Record {          
    private final static String RSTRUE = String.valueOf(true);    
    private boolean[] wtShow;
    private int wtCompare;
    private boolean sLabels;
    private boolean sAscending;
    private boolean iCase;
    
    public RecordsStatus (final int nlbl) {
        setWhatToShow(Constants.SHWKEYVALUE);
        setWhatToCompare(Constants.CMPKEY);
        setSortAscending(true);
        setIgnoreCase(true);
        setShowLabels(false);
        String[] lbls = new String [nlbl];
        if (nlbl >0) lbls[0] = Strings.KEY;
        for (int i=1;i<nlbl;++i)  lbls[i] = new StringBuffer(Strings.VALUE).append(Integer.toString(i)).toString();       
        setLabels (lbls);        
    }
    
    public RecordsStatus(Record r) {
        setValues(r.getValues());
        setLabels(r.getLabels());
        setRecordID(r.getRecordID());
        setModified(false); //because just made
    }

    public void setLabels(String[] keyNames) {
        super.setLabels(keyNames);
        commonLabels = keyNames;
    }
            
    private static boolean CompareBooleanArrays (boolean[] a, boolean[] b) {
        if (a == null || b == null) {
            if (a == null && b == null) return true;
            return false;
        } 
        if (a.length != b.length) return false;
        for (int i=0; i<a.length; ++i) if (a[i] != b[i]) return false;
        return true;
    }
  
    private static String booleanArrayToString (boolean[] ba) {
        StringBuffer result = new StringBuffer ();
        for (int i=0; i<ba.length; ++i) 
            if (ba[i]) result.append(Constants.TRUE);
            else result.append(Constants.FALSE);
        return result.toString();                        
    }
    
    private static boolean[] stringToBooleanArray (String s) {
        boolean[]  result = new boolean[s.length()];
        final int len = s.length();
        for (int i=0; i<len; ++i) 
            if (s.charAt(i) == Constants.TRUE) result[i] = true;
            else result[i] = false;
        return result;                        
    }
    
    public void readRecordsStatus () {        
        boolean notChanged = true;               
        notChanged &= (CompareBooleanArrays(wtShow, getWhatToShow()));        
        notChanged &= (wtCompare == getWhatToCompare());        
        notChanged &= (sAscending == isSortAscending());        
        notChanged &= (iCase == isIgnoreCase());        
        notChanged &= (sLabels  == isShowLabels());
        
        if (! notChanged) {
            String[] val = new String[6];
            val[0] = Constants.STATUSKEYS[Constants.STATUSKEYS.length-1]; //something strange enough for the user to insert as ba key...
            val[1] = booleanArrayToString(wtShow = getWhatToShow());
            val[2] = String.valueOf(wtCompare = getWhatToCompare());
            val[3] = String.valueOf(sAscending = isSortAscending());        
            val[4] = String.valueOf(iCase = isIgnoreCase());                
            val[5] = String.valueOf(sLabels = isShowLabels());                
            setValues(val);
        }
    }
    
    public void writedRecordsStatus () {        
        if (! isLatestRecordStatusKey(getValue(0))) return; 
        if (getNumberOfValues() != 6) return;
        wtShow = stringToBooleanArray(getValue(1));
        setWhatToShow(wtShow);
        wtCompare = Integer.parseInt(getValue(2));
        setWhatToCompare(wtCompare);
        sAscending = (getValue(3).equals(RSTRUE));
        setSortAscending(sAscending);
        iCase = (getValue(4).equals(RSTRUE));
        setIgnoreCase(iCase);
        sLabels = (getValue(5).equals(RSTRUE));
        setShowLabels(sLabels);
        commonLabels = getLabels();
    }    
    
    public static boolean isARecordStatusKey (String key) {
        for (int i=0; i<Constants.STATUSKEYS.length; ++i)
            if (key.equals(Constants.STATUSKEYS[i])) return true;
        return false;
    }
    
    public static boolean isLatestRecordStatusKey(String key) {
        return key.equals(Constants.STATUSKEYS[Constants.STATUSKEYS.length-1]);
    }    
    
}
