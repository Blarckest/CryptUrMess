package uqac.dim.crypturmess.model.entity;
import android.util.Base64;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Timestamp;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.utils.crypter.IDecrypter;

/**
 * This class represent a message.
 */
@Entity(tableName = "Message", foreignKeys = {
        @ForeignKey(
                entity = Conversation.class,
                parentColumns = "id",
                childColumns = "id_conversation"
        )
}, indices={
        @Index(value = "id_conversation")
})
public class Message {
    @ColumnInfo(name = "id_message")
    @PrimaryKey(autoGenerate = true)
    private int idMessage;
    @ColumnInfo(name = "id_conversation")
    private int idConversation;
    @ColumnInfo(name = "message_content")
    private String message;
    @ColumnInfo(name = "timestamp")
    private long timestamp;
    @ColumnInfo(name = "isReceived")
    private boolean isReceived;

    /**
     * Empty constructor
     */
    public Message() {
    }

    @Ignore
    public Message(CryptedMessage message, IDecrypter decrypter, boolean insertIntoDatabase, boolean isReceived) {
        AppLocalDatabase db = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idCorrespondant = user.getUid()==message.getIdSender()?message.getIdReceiver():message.getIdSender();
        Conversation conv = db.conversationDao().getConversation(idCorrespondant);
        this.idConversation = conv.getIdConversation();
        this.message = decrypter.decrypt(Base64.decode(message.getMessage(), Base64.DEFAULT));
        this.timestamp = message.getTimestamp();
        if(insertIntoDatabase) {
            db.messageDao().insert(this);
        }
        else {
            this.idMessage = -1;
        }

        this.isReceived = isReceived;
    }

    /**
     * Constructor minimal
     */
    @Ignore
    public Message(String message, int idConversation) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        setTimestamp(timestamp.getTime());
        setIdConversation(idConversation);
        setMessage(message);
    }

    /**
     * Constructor full parameters
     */
    @Ignore
    public Message(String message, int idConversation, long timestamp) {
        this(message, idConversation);
        setTimestamp(timestamp);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean isReceived) {
        this.isReceived = isReceived;
    }
}
