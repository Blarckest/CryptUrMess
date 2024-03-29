package uqac.dim.crypturmess.model.entity;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Timestamp;
import java.util.Objects;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.utils.crypter.IDecrypter;

/**
 * This class represent a message.
 */
@Entity(tableName = "Message", foreignKeys = {
        @ForeignKey(
                entity = Conversation.class,
                parentColumns = "id_conv",
                childColumns = "id_conversation",
                onDelete=ForeignKey.CASCADE
        )
}, indices={
        @Index(value = "id_conversation")
})
public class Message {
    @ColumnInfo(name = "id_message")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idMessage;
    @ColumnInfo(name = "id_conversation")
    private Integer idConversation;
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
        if(conv==null){
            UserClientSide[] users = db.userDao().getFriends();
            conv = new Conversation(idCorrespondant);
            db.conversationDao().insert(conv);
            this.idConversation = db.conversationDao().getConversation(idCorrespondant).getIdConversation();
        }
        else
            this.idConversation = conv.getIdConversation();
        this.message = decrypter.decrypt(Base64.decode(message.getMessage(), Base64.DEFAULT));
        this.timestamp = message.getTimestamp();
        this.isReceived = isReceived;
        if(insertIntoDatabase) {
            db.messageDao().insertAndNotify(this);
        }
        else {
            this.idMessage = -1;
        }
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

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return getTimestamp() == message1.getTimestamp() && isReceived() == message1.isReceived() && getMessage().equals(message1.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getTimestamp());
    }
}
