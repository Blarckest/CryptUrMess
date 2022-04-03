package uqac.dim.crypturmess.model.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

/**
 * This class represent a message.
 */
@Entity(tableName = "Message", foreignKeys = {
        @ForeignKey(
                entity = Conversation.class,
                parentColumns = "id",
                childColumns = "id_conversation"
        )
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

    /**
     * Empty constructor
     */
    public Message() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        setTimestamp(timestamp.getTime());
    }

    /**
     * Constructor minimal
     */
    @Ignore
    public Message(String message, int idConversation) {
        this();
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
}
