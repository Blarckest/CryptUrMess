package uqac.dim.crypturmess.utils.crypter.signature;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import uqac.dim.crypturmess.utils.crypter.ISigner;
import uqac.dim.crypturmess.utils.crypter.keys.IKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.RSA.RSASignKeysManager;

public class Signer implements ISigner {
    private IKeysManager keysToSign=new RSASignKeysManager();
    private Cipher cipher;
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
    public boolean verifySignature(byte[] encrypted, int friendId) {
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
            bytes=cipher.doFinal(encrypted);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return Signature.verify(bytes, version);
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
