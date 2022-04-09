package uqac.dim.crypturmess.utils.crypter.keys;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.PrivateKey;

import javax.crypto.SecretKey;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.IKeyInitializer;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.KeyInitializer;

public class AESKeysManager implements ISecretKeyManager {
    private Algorithm algorithm= Algorithm.AES;
    private int keySize=256;

    @Override
    public SecretKey getSecretKey() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        String secretKey= sharedPreferencesHelper.getValue(R.string.AESKeySharedPref);
        IKeyInitializer keyInitializer = new KeyInitializer();
        if(secretKey.equals("")) {
            SecretKey key = keyInitializer.generateSecretKey(algorithm,keySize);
            saveKeyLocally(key);
            return key;
        }
        else {
            return keyInitializer.createKeyFromKeyBytes(algorithm,Base64.decode(secretKey.getBytes(),Base64.DEFAULT));
        }
    }

    private void saveKeyLocally(SecretKey secretKey) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        sharedPreferencesHelper.setValue(R.string.AESKeySharedPref, Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));
    }
}
