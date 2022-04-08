package uqac.dim.crypturmess.utils.crypter.keys.keyInitializer;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.AlgorithmsSpec;

public class KeyInitializer implements IKeyInitializer {
    @Override
    public SecretKey generateSecretKey(Algorithm algorithm, int keySize) {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance(algorithm.toString());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        keyGen.init(keySize);
        return keyGen.generateKey();
    }

    @Override
    public KeyPair generateKeyPair(Algorithm algorithm, int keySize) {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance(algorithm.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        keyGen.initialize(keySize);
        return keyGen.generateKeyPair();
    }

    @Override
    public SecretKey createKeyFromKeyBytes(Algorithm algorithm, byte[] keyBytes) {
        if (algorithm.equals(AlgorithmsSpec.AES)){
            SecretKeyFactory keyFactory = null;
            try {
                SecretKeyFactory.getInstance(algorithm.toString());
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, algorithm.toString());
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
    public Key createKeyFromKeyBytes(Algorithm algorithm, byte[] keyBytes, boolean isPrivate) {
        if (algorithm.equals(Algorithm.RSA)){
            KeyFactory keyFactory = null;
            try {
                keyFactory=KeyFactory.getInstance(algorithm.toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
            if (isPrivate){
                try {
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                    return keyFactory.generatePrivate(keySpec);
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            else {
                try {
                    X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
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
