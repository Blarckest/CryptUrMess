package uqac.dim.crypturmess.utils.crypter.AES;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import uqac.dim.crypturmess.utils.crypter.Algorithms;
import uqac.dim.crypturmess.utils.crypter.CipherInitializer;
import uqac.dim.crypturmess.utils.crypter.IDecrypter;
import uqac.dim.crypturmess.utils.crypter.keys.AESKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.IKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.ISecretKeyManager;

public class AESDecrypter implements IDecrypter {
    private Cipher cipher;
    private ISecretKeyManager myKeys =new AESKeysManager();
    @Override
    public String decrypt(byte[] cypherText) {
        CipherInitializer.initCipher(cipher, Algorithms.AES);
        try {
            throw new InvalidKeyException();
            //todo get the key
            //cipher.init(Cipher.DECRYPT_MODE, myKeys.getSecretKey());
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
