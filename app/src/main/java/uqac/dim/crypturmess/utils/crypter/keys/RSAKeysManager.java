package uqac.dim.crypturmess.utils.crypter.keys;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.IKeyInitializer;
import uqac.dim.crypturmess.utils.crypter.keys.keyInitializer.KeyInitializer;

public class RSAKeysManager implements IKeysManager {
    private String algorithm="RSA/ECB/PKCS1Padding";
    private int keySize=2048;

    @Override
    public PublicKey getPublicKey() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./RSApublic.key"))) {
            return (PublicKey) ois.readObject();
        } catch (Exception e) {
            IKeyInitializer keyInitializer = new KeyInitializer();
            KeyPair pair = keyInitializer.generateKeyPair(algorithm,keySize);
            saveKeys(pair.getPrivate(), pair.getPublic());
            return getPublicKey();
        }
    }

    @Override
    public PrivateKey getPrivateKey() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./RSAprivate.key"))) {
            return (PrivateKey) ois.readObject();
        } catch (Exception e) {
            IKeyInitializer keyInitializer = new KeyInitializer();
            KeyPair pair = keyInitializer.generateKeyPair(algorithm,keySize);
            saveKeys(pair.getPrivate(), pair.getPublic());
            return getPrivateKey();
        }
    }

    private void saveKeys(PrivateKey privateKey, PublicKey publicKey) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./RSAprivate.key"))) {
            oos.writeObject(privateKey);
            //todo save key to firebase here
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./RSApublic.key"))) {
            oos.writeObject(publicKey);
            //todo save key to firebase here

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
