package uqac.dim.crypturmess.utils.crypter.RSA;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import uqac.dim.crypturmess.utils.crypter.Algorithms;
import uqac.dim.crypturmess.utils.crypter.CipherInitializer;
import uqac.dim.crypturmess.utils.crypter.IDecrypter;
import uqac.dim.crypturmess.utils.crypter.keys.IKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.RSAKeysManager;

public class RSADecrypter implements IDecrypter {
    private Cipher cipher;
    private IKeysManager myKeys =new RSAKeysManager();

    @Override
    public String decrypt(byte[] cypherText) {
       CipherInitializer.initCipher(cipher, Algorithms.RSA);
        try {
            cipher.init(Cipher.DECRYPT_MODE, myKeys.getPrivateKey());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] bytes=null;
        try {
            bytes=cipher.doFinal(cypherText);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }
}

