package uqac.dim.crypturmess.databaseAccess.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.User;

public interface IDatabaseHelper {
    void sendMessage(CryptedMessage cryptedMessage);
    void pushRSAPublicKey();
    void saveUser(User user);

    Task<DataSnapshot> getUser(String uid);

    Task<DataSnapshot> getRSAPublicKeyOfUser(String uid);
}
