package uqac.dim.crypturmess.utils.crypter.keys.RSA;

import android.util.Base64;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.utils.crypter.Algorithms;
import uqac.dim.crypturmess.utils.crypter.keys.IKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.IKeyInitializer;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.KeyInitializer;

public class RSASignKeysManager implements IKeysManager {
    private String algorithm="RSA/ECB/PKCS1Padding";
    private int keySize=2048;

    @Override
    public PublicKey getPublicKey() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        String publicKey=sharedPreferencesHelper.getValue("publicKeySign");
        IKeyInitializer keyInitializer = new KeyInitializer();
        if(publicKey.equals("")) {
            KeyPair pair = keyInitializer.generateKeyPair(algorithm,keySize);
            saveKeysLocally(pair.getPrivate(), pair.getPublic());
            return getPublicKey();
        }
        else {
            return (PublicKey) keyInitializer.createKeyFromKeyBytes(Algorithms.RSA,Base64.decode(publicKey.getBytes(),Base64.DEFAULT),false);
        }
    }

    @Override
    public PrivateKey getPrivateKey() {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        String privateKey= sharedPreferencesHelper.getValue("privateKeySign");
        IKeyInitializer keyInitializer = new KeyInitializer();
        if(privateKey.equals("")) {
            KeyPair pair = keyInitializer.generateKeyPair(algorithm,keySize);
            saveKeysLocally(pair.getPrivate(), pair.getPublic());
            return getPrivateKey();
        }
        else {
            return (PrivateKey) keyInitializer.createKeyFromKeyBytes(Algorithms.RSA,Base64.decode(privateKey.getBytes(),Base64.DEFAULT),true);
        }
    }

    private void saveKeysLocally(PrivateKey privateKey, PublicKey publicKey) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
        sharedPreferencesHelper.setValue("privateKeySign", Base64.encodeToString(privateKey.getEncoded(), Base64.DEFAULT));
        sharedPreferencesHelper.setValue("publicKeySign",Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT));
    }
}
