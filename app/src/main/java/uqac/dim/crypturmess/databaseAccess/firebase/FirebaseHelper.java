package uqac.dim.crypturmess.databaseAccess.firebase;

import android.util.Base64;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.utils.auth.FirebaseAuthManager;
import uqac.dim.crypturmess.utils.auth.IAuthManager;
import uqac.dim.crypturmess.utils.crypter.keys.IKeysManager;
import uqac.dim.crypturmess.utils.crypter.keys.RSAKeysManager;

public class FirebaseHelper implements IDatabaseHelper {
    private IKeysManager keysManager=new RSAKeysManager();
    private IAuthManager authManager=new FirebaseAuthManager();
    private DatabaseReference db= FirebaseDatabase.getInstance().getReference();
    @Override
    public void sendMessage(CryptedMessage cryptedMessage) {
        db.child("messages").child(String.valueOf(cryptedMessage.getIdReceiver())).setValue(cryptedMessage);
    }

    @Override
    public void pushRSAPublicKey() {
        db.child("keys").child("RSA").child(authManager.getCurrentUser().getUid()).setValue(Base64.encodeToString(keysManager.getPublicKey().getEncoded(),Base64.DEFAULT));
    }

    @Override
    public void saveUser(User user) {
        db.child("users").child(authManager.getCurrentUser().getUid()).setValue(user);
    }
}
