package uqac.dim.crypturmess.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * This class represent a conversation between two users.
 */
@Entity(tableName = "Conversation", foreignKeys = {
        @ForeignKey(
                entity = UserClientSide.class,
                parentColumns = "id",
                childColumns = "id_correspondant",
                onDelete=ForeignKey.CASCADE
        )
}, indices={
        @Index(value = {"id_correspondant"}),
})
public class Conversation {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idConversation;
    @ColumnInfo(name = "id_correspondant")
    private String idCorrespondant;
    @Ignore
    private final ArrayList<Message> listMessages;

    /**
     * Empty constructor
     */
    public Conversation() {
        listMessages = new ArrayList<>();}

    /**
     * Construction
     */
    @Ignore
    public Conversation(String idCorrespondant) {
        this();
        setIdCorrespondant(idCorrespondant);
    }

    public ArrayList<Message> getListMessages() {
        return listMessages;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public String getIdCorrespondant() {
        return idCorrespondant;
    }

    public void setIdCorrespondant(String idCorrespondant) {
        this.idCorrespondant = idCorrespondant;
    }
}
