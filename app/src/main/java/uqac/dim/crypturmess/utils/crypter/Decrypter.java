package uqac.dim.crypturmess.utils.crypter;

public interface Decrypter extends SignatureVersion {
   boolean verifySignature(byte[] encrypted, int friendId);
   String decrypt(byte[] cypherText);
}
