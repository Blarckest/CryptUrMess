package uqac.dim.crypturmess.utils.crypter.RSA;

import android.util.Base64;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.utils.crypter.Algorithms;
import uqac.dim.crypturmess.utils.crypter.CipherInitializer;
import uqac.dim.crypturmess.utils.crypter.ICrypter;
import uqac.dim.crypturmess.utils.crypter.keys.IKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.RSA.RSAKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.KeyInitializer;

public class RSACrypter implements ICrypter {
    private Cipher cipher;
    private IKeysManager myKeys =new RSAKeysManager();

    @Override
    public byte[] encryptToSend(String plaintext, String friendId) {
        CipherInitializer.initCipher(cipher, Algorithms.RSA);
        try {
            UserClientSide user = AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).userDao().getUserById(friendId);
            PublicKey publicKey = (PublicKey) new KeyInitializer().createKeyFromKeyBytes(Algorithms.RSA, Base64.decode(user.getRsaPublicKey(),Base64.DEFAULT),false);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
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
}
