package uqac.dim.crypturmess.utils.crypter.keys.keyInitializer;

import java.security.Key;
import java.security.KeyPair;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

public interface IKeyInitializer {
    SecretKey generateSecretKey(String algorithm, int keySize);
    KeyPair generateKeyPair(String algorithm, int keySize);
    SecretKey createKeyFromKeyBytes(String algorithm, byte[] keyBytes);
    Key createKeyFromKeyBytes(String algorithm, byte[] keyBytes, boolean isPrivate);

}
