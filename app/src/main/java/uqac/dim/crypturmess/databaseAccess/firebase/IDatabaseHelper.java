package uqac.dim.crypturmess.databaseAccess.firebase;

import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.User;

public interface IDatabaseHelper {
    void sendMessage(CryptedMessage cryptedMessage);
    void pushRSAPublicKey();
    void saveUser(User user);
}
