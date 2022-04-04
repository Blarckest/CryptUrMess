package uqac.dim.crypturmess.utils.crypter;

public interface ICrypter extends SignatureVersion {
    byte[] signMessage(String plaintext);
    byte[] encryptToSend(String plaintext, String friendId);
}
