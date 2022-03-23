package uqac.dim.crypturmess.utils.crypter.keys;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IKeysManager {
    PublicKey getPublicKey();
    PrivateKey getPrivateKey();
}
