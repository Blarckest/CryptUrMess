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
    @Query("SELECT * FROM Conversation WHERE id_correspondant = id_correspondant=:id_correspondant")
    Conversation getConversation(String id_correspondant);
    @Query("SELECT * FROM Conversation WHERE id = :id")
    Conversation getConversationById(int id);
}
