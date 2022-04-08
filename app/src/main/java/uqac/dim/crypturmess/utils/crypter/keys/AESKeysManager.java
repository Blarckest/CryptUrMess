package uqac.dim.crypturmess.utils.crypter.keys;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.SecretKey;

import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.AlgorithmsSpec;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.IKeyInitializer;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.KeyInitializer;

public class AESKeysManager implements ISecretKeyManager {
    private Algorithm algorithm= Algorithm.AES;
    private int keySize=256;

    @Override
    public SecretKey getSecretKey() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./AES.key"))) {
            return (SecretKey) ois.readObject();
        } catch (Exception e) {
            IKeyInitializer keyInitializer = new KeyInitializer();
            SecretKey key = keyInitializer.generateSecretKey(algorithm,keySize);
            saveKeys(key);
            return key;
        }
    }

    private void saveKeys(SecretKey Key) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./AES.key"))) {
            oos.writeObject(Key);
            //todo save key to firebase here
        }
        catch (Exception e) {
            getSecretKey(); //create key
            e.printStackTrace();
        }
    }
}
