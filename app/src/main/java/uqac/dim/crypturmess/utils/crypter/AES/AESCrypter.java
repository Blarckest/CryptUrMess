package uqac.dim.crypturmess.utils.crypter.AES;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.AlgorithmsSpec;
import uqac.dim.crypturmess.utils.crypter.CipherInitializer;
import uqac.dim.crypturmess.utils.crypter.ICrypter;

public class AESCrypter implements ICrypter {
    private Cipher cipher;
    @Override
    public byte[] encryptToSend(String plaintext, String friendId) {
        cipher=CipherInitializer.initCipher(AlgorithmsSpec.AES);
        try {
            throw new InvalidKeyException("Not implemented");
            //todo get la  key
            //cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(friendId));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] bytes=null;
        try {
            bytes=cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.AES;
    }
}
