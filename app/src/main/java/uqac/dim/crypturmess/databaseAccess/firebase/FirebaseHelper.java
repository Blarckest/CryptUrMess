package uqac.dim.crypturmess.databaseAccess.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.utils.crypter.keys.IKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.RSAKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.RSASignKeysManager;

public class FirebaseHelper implements IDatabaseHelper {
    private IKeysManager keysManager=new RSAKeysManager();
    private IKeysManager signKeyManager=new RSASignKeysManager();
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private DatabaseReference db= FirebaseDatabase.getInstance().getReference();
    @Override
    public void sendMessage(CryptedMessage cryptedMessage) {
        db.child("messages").child(String.valueOf(cryptedMessage.getIdReceiver())).setValue(cryptedMessage);
    }

    @Override
    public void pushRSAPublicKey(User user) {
        db.child("keys").child("RSA").child(auth.getUid()).setValue(keysManager.getPublicKey());
    }

    @Override
    public void pushPrivateKeyForSignature(User user) {
        db.child("keys").child("RSAsign").child(auth.getUid()).setValue(signKeyManager.getPrivateKey());
    }

    @Override
    public void saveUser(User user) {
        db.child("users").child(auth.getUid()).setValue(user);
    }
}
