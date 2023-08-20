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
 * Cypher.java
 *
 * Created on 6 dicembre 2003, 21.48
 */

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import org.bouncycastle.crypto.digests.*;
import org.bouncycastle.crypto.generators.*;
import org.bouncycastle.util.encoders.*;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.paddings.*;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.*;

/**
 *
 * @author  Simone
 * @version
 */
public final class Cypher {
    private CipherParameters cpPassword;
    private BufferedBlockCipher bbCipher;
    private boolean cypher=true;
    
    public Cypher (String password) {        
        if (password == null || password.length() ==0) {
            cypher = false;
        } else {
            PBEParametersGenerator  generator = new PKCS12ParametersGenerator(new SHA1Digest());
            generator.init(PBEParametersGenerator.PKCS12PasswordToBytes(password.toCharArray()), Hex.decode("0A58CF64530D823F"), 1);
            cpPassword = generator.generateDerivedParameters(256);
            bbCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
        }
    }    
            
    public byte[] crypte (byte[] data) {        
        return encrypt (data, true);
    }
    
    public byte[] unCrypte (byte[] data) {                
        return encrypt (data, false);
    }
    
    public boolean isCypher() {
        return cypher;
    }        

    private byte[] encrypt(byte[] data, boolean forEncription) {
        if (!cypher) return data;
        
        bbCipher.init(forEncription, cpPassword);
        
        byte[] oData = new byte [bbCipher.getOutputSize(data.length)];
        
        int oLen = bbCipher.processBytes(data, 0, data.length, oData, 0);
        try {            
            bbCipher.doFinal(oData, oLen);
        } catch (Exception ex) {
            return null;//this can happen if the cpPassword is not correct, returns null to get an exception            
        }

        return oData;        
    }
}
