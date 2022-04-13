package uqac.dim.crypturmess.model.entity;

import android.util.Base64;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.AlgorithmsSpec;
import uqac.dim.crypturmess.utils.crypter.ICrypter;

/**
 * This class represent a message.
 */

public class CryptedMessage {
    private String message;
    private String idSender;
    private String idReceiver;
    private long timestamp;
    private Algorithm algorithm;

    public CryptedMessage() {}

    /**
     * Constructor
     */
    public CryptedMessage(String message, String idSender, String idReceiver, long date, Algorithm algorithm) {
        setMessage(message);
        setIdSender(idSender);
        setIdReceiver(idReceiver);
        setTimestamp(date);
        setAlgorithm(algorithm);
    }

    public CryptedMessage(Message message, ICrypter crypter, boolean isReceived) {
        setAlgorithm(crypter.getAlgorithm());
        AppLocalDatabase db = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
        Conversation conv = db.conversationDao().getConversationById(message.getIdConversation());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (isReceived) {
            setIdReceiver(user.getUid());
            setIdSender(conv.getIdCorrespondant());
        }
        else {
            setIdReceiver(conv.getIdCorrespondant());
            setIdSender(user.getUid());
        }
        this.message = Base64.encodeToString(crypter.encryptToSend(message.getMessage(),conv.getIdCorrespondant()), Base64.DEFAULT);
        this.timestamp = message.getTimestamp();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
