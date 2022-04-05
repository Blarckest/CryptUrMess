package uqac.dim.crypturmess.utils.crypter.keys.keyInitializer;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import uqac.dim.crypturmess.utils.crypter.Algorithms;

public class KeyInitializer implements IKeyInitializer {
    @Override
    public SecretKey generateSecretKey(String algorithm, int keySize) {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        keyGen.init(keySize);
        return keyGen.generateKey();
    }

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

    @Override
    public SecretKey createKeyFromKeyBytes(String algorithm, byte[] keyBytes) {
        if (algorithm.equals(Algorithms.AES)){
            SecretKeyFactory keyFactory = null;
            try {
                SecretKeyFactory.getInstance(algorithm);
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, algorithm);
            try {
                keyFactory.generateSecret(keySpec);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public Key createKeyFromKeyBytes(String algorithm, byte[] keyBytes, boolean isPrivate) {
        if (algorithm.equals(Algorithms.RSA)){
            KeyFactory keyFactory = null;
            try {
                KeyFactory.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            try {
                keyFactory.generatePrivate(keySpec);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                return null;
            }
            if (isPrivate){
                try {
                    return keyFactory.generatePrivate(keySpec);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            else {
                try {
                    return keyFactory.generatePublic(keySpec);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }
}
