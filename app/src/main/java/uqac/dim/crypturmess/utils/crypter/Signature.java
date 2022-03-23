package uqac.dim.crypturmess.utils.crypter;

import java.util.HashMap;
import java.util.Map;

//class Signature define the message to encode in case of signature demand, hashmap contains the signature for version of the application
public class Signature {
    static public Map<Integer,String> signatures=new HashMap<>();
    static {
        signatures.put(0,"e312eed607dd70613bf9304f668be467");
    }

    public static boolean verify(byte[] bytes, int version) {
        return signatures.get(version).equals(new String(bytes));
    }
}
