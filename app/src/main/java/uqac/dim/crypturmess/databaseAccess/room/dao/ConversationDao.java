package uqac.dim.crypturmess.databaseAccess.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import uqac.dim.crypturmess.model.entity.Conversation;

@Dao
public interface ConversationDao {
    @Delete
    void delete(Conversation conversationid);
    @Insert
    void insert(Conversation conversationid);
    @Query("SELECT * FROM Conversation WHERE id_user_1 = :id1 AND id_user_2 = :id2 or id_user_2 = :id2 AND id_user_1 = :id1")
    Conversation getConversation(int id1, int id2);
    @Query("SELECT * FROM Conversation WHERE id = :id")
    Conversation getConversationById(int id);
}
