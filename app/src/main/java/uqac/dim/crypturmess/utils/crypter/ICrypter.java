package uqac.dim.crypturmess.utils.crypter;
public interface ICrypter {
    byte[] encryptToSend(String plaintext, String friendId);
}
