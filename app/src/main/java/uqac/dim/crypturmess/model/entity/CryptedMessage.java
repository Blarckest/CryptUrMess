package uqac.dim.crypturmess.model.entity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.utils.crypter.Crypter;
import uqac.dim.crypturmess.utils.crypter.RSACrypter;

/**
 * This class represent a message.
 */

public class CryptedMessage {
    private byte[] message;
    private String idSender;
    private String idReceiver;
    private long timestamp;

    /**
     * Constructor
     */
    public CryptedMessage(byte[] message, String idSender, String idReceiver, long date) {
        setMessage(message);
        setIdSender(idSender);
        setIdReceiver(idReceiver);
        setTimestamp(date);
    }

    public CryptedMessage(Message message, boolean isReceived) {
        AppLocalDatabase db = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
        Conversation conv = db.conversationDao().getConversationById(message.getIdConversation());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (isReceived) {
            setIdReceiver(conv.getIdCorrespondant());
            setIdSender(user.getUid());
        }
        else {
            setIdReceiver(user.getUid());
            setIdSender(conv.getIdCorrespondant());
        }

        Crypter crypter = new RSACrypter();
        this.message = crypter.encryptToSend(message.getMessage(),conv.getIdCorrespondant());
        this.timestamp = message.getTimestamp();
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
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
}
