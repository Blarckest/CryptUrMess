package uqac.dim.crypturmess.databaseAccess.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;

import uqac.dim.crypturmess.model.entity.Conversation;

@Dao
public interface ConversationDao {
    @Delete
    void delete(Conversation conversation);
}
