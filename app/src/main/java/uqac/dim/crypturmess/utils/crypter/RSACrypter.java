package uqac.dim.crypturmess.utils.crypter;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import uqac.dim.crypturmess.utils.crypter.keys.IKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.RSAKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.RSASignKeysManager;

public class RSACrypter implements Crypter {
    private Cipher cipher;
    private IKeysManager keysToSign=new RSASignKeysManager();
    private IKeysManager myKeys =new RSAKeysManager();
    @Override
    public byte[] signMessage(String plaintext) {
        initCipher();
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keysToSign.getPrivateKey());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] bytes=null;
        try {
            bytes=cipher.doFinal(Signature.signatures.get(version).getBytes(StandardCharsets.UTF_8));
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public byte[] encryptToSend(String plaintext, String friendId) {
        initCipher();
        try {
            throw new InvalidKeyException("Not implemented");
            //todo get la public key
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

    private boolean initCipher() {
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
