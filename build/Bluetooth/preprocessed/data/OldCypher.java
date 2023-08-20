package data;
/*
 * OldCypher.java
 *
 * Created on 6 dicembre 2003, 21.48
 */
import java.util.Random;
/**
 *
 * @author  Simone
 * @version
 */
public final class OldCypher {
    private long password;
    private Random sequence;
    private boolean lastCrypte;
    private boolean cypher=true;
    
    public OldCypher (long password) {
        if (password == 0) cypher = false;
        this.password = password;
        this.reset();
    }    
    
    private void reset () {
        sequence = new Random(password);
    }
        
    
    public byte[] crypte (byte[] data) {        
        if (!cypher) return data;
        reset ();
        for (int i=0; i<data.length; ++i) {
            data[i] = (byte) (data[i] ^ sequence.nextInt()); 
        }
        return data;
    }
    
    public byte[] unCrypte (byte[] data) {
        return crypte(data);
    }
    
    /** Getter for property OldCypher.
     * @return Value of property OldCypher.
     */
    public boolean isCypher() {
        return cypher;
    }        
}
