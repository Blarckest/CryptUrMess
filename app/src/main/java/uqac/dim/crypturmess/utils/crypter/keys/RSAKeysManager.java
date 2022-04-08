package uqac.dim.crypturmess.utils.crypter.keys;

import android.util.Base64;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.AlgorithmsSpec;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.IKeyInitializer;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.KeyInitializer;

public class RSAKeysManager implements IKeysManager {
    private Algorithm algorithm= Algorithm.RSA.RSA;
    private int keySize=2048;

    @Override
    public PublicKey getPublicKey() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        String publicKey=sharedPreferencesHelper.getValue(R.string.publicKeySharedPref);
        IKeyInitializer keyInitializer = new KeyInitializer();
        if(publicKey.equals("")) {
            KeyPair pair = keyInitializer.generateKeyPair(algorithm,keySize);
            saveKeysLocally(pair.getPrivate(), pair.getPublic());
            return getPublicKey();
        }
        else {
            return (PublicKey) keyInitializer.createKeyFromKeyBytes(algorithm,Base64.decode(publicKey.getBytes(),Base64.DEFAULT),false);
        }
    }

    @Override
    public PrivateKey getPrivateKey() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        String privateKey= sharedPreferencesHelper.getValue(R.string.privateKeySharedPref);
        IKeyInitializer keyInitializer = new KeyInitializer();
        if(privateKey.equals("")) {
            KeyPair pair = keyInitializer.generateKeyPair(algorithm,keySize);
            saveKeysLocally(pair.getPrivate(), pair.getPublic());
            return getPrivateKey();
        }
        else {
            return (PrivateKey) keyInitializer.createKeyFromKeyBytes(algorithm,Base64.decode(privateKey.getBytes(),Base64.DEFAULT),true);
        }
    }

    private void saveKeysLocally(PrivateKey privateKey, PublicKey publicKey) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        sharedPreferencesHelper.setValue(R.string.privateKeySharedPref, Base64.encodeToString(privateKey.getEncoded(), Base64.DEFAULT));
        sharedPreferencesHelper.setValue(R.string.publicKeySharedPref,Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT));
    }
}
