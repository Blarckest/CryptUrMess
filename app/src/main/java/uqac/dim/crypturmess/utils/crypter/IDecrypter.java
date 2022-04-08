package uqac.dim.crypturmess.utils.crypter;

public interface IDecrypter{
   String decrypt(byte[] cypherText);
   Algorithm getAlgorithm();
}
