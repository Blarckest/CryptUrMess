package uqac.dim.crypturmess.utils.crypter;

import uqac.dim.crypturmess.utils.crypter.signature.SignatureVersion;

public interface ISigner extends SignatureVersion {
    boolean verifySignature(byte[] encrypted, int friendId);
    byte[] signMessage(String plaintext);
}
