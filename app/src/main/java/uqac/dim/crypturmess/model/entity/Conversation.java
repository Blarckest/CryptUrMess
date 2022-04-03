package uqac.dim.crypturmess.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * This class represent a conversation between two users.
 */
@Entity(tableName = "Conversation", foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "id_user_1"
        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "id_user_2"
        )
})
public class Conversation {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int idConversation;
    @ColumnInfo(name = "id_user_1")
    private int idUser1;
    @ColumnInfo(name = "id_user_2")
    private int idUser2;
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
    public Conversation(int idUser1, int idUser2) {
        this();
        setIdUser1(idUser1);
        setIdUser2(idUser2);
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

    public int getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(int idUser1) {
        this.idUser1 = idUser1;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }
}
