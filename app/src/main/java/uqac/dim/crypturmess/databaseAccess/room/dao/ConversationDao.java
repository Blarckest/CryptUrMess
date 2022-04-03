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
    @Query("SELECT * FROM conversation WHERE id1 = :id1 AND id2 = :id2 or id1 = :id2 AND id2 = :id1")
    Conversation getConversation(int id1, int id2);
    @Query("SELECT * FROM conversation WHERE id = :id")
    Conversation getConversationById(int id);
}
