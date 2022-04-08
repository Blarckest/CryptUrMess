package uqac.dim.crypturmess.utils.crypter.keys.keyInitializer;

import java.security.Key;
import java.security.KeyPair;

import javax.crypto.SecretKey;

import uqac.dim.crypturmess.utils.crypter.Algorithm;

public interface IKeyInitializer {
    SecretKey generateSecretKey(Algorithm algorithm, int keySize);
    KeyPair generateKeyPair(Algorithm algorithm, int keySize);
    SecretKey createKeyFromKeyBytes(Algorithm algorithm, byte[] keyBytes);
    Key createKeyFromKeyBytes(Algorithm algorithm, byte[] keyBytes, boolean isPrivate);

}
