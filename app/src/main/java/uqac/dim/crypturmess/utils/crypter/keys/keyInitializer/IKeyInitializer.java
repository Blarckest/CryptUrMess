package uqac.dim.crypturmess.utils.crypter.keys.keyInitializer;

import java.security.Key;
import java.security.KeyPair;

public interface IKeyInitializer {
    KeyPair generateKeyPair(String algorithm, int keySize);
}
