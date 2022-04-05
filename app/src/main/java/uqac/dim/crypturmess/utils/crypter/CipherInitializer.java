package uqac.dim.crypturmess.utils.crypter;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class CipherInitializer {
    public static boolean initCipher(Cipher cipher, String algorithm) {
        try {
            cipher = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
