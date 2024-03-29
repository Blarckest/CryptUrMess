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
                parentColumns = "id_user",
                childColumns = "id_correspondant",
                onDelete=ForeignKey.CASCADE
        )
}, indices={
        @Index(value = {"id_correspondant"}),
})
public class Conversation {
    @ColumnInfo(name = "id_conv")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer idConversation;
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

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
        this.idConversation = idConversation;
    }

    public String getIdCorrespondant() {
        return idCorrespondant;
    }

    public void setIdCorrespondant(String idCorrespondant) {
        this.idCorrespondant = idCorrespondant;
    }
}
