package uqac.dim.crypturmess.utils.crypter.keys.keyInitializer;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyInitializer implements IKeyInitializer {
    @Override
    public KeyPair generateKeyPair(String algorithm, int keySize) {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        keyGen.initialize(keySize);
        return keyGen.generateKeyPair();
    }
}
