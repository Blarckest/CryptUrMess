package uqac.dim.crypturmess.utils.crypter;

public interface Crypter extends SignatureVersion {
    byte[] signMessage(String plaintext);
    byte[] encryptToSend(String plaintext, String friendId);
}
