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
