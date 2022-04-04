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
    private Date date;

    /**
     * Constructor
     */
    public CryptedMessage(byte[] message, String idSender, String idReceiver, Date date) {
        setMessage(message);
        setIdSender(idSender);
        setIdReceiver(idReceiver);
        setDate(date);
    }

    public CryptedMessage(Message message, boolean isReceived) {
//        AppLocalDatabase db = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
//        Conversation conv = db.conversationDao().getConversationById(message.getIdConversation());
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (isReceived){
//            if (user.getUid() == conv.getIdUser1()) {
//                setIdReceiver(conv.getIdUser2());
//                setIdSender(conv.getIdUser1());
//            }
//        }
//        else{
//            setIdReceiver(conv.getIdUser1());
//            setIdSender(conv.getIdUser2());
//        }

        Crypter crypter = new RSACrypter();
        /*this.message = crypter.encryptToSend(message.getMessage(),message.getIdReceiver());
        this.idSender = message.getIdSender();
        this.idReceiver = message.getIdReceiver();
        this.date = message.getDate();*/
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
