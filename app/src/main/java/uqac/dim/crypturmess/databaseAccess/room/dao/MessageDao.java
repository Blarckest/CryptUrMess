package uqac.dim.crypturmess.databaseAccess.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import uqac.dim.crypturmess.model.entity.Message;

@Dao
public interface MessageDao {
    @Delete
    void delete(Message message);
    @Query("DELETE FROM Message WHERE id_message = :id")
    void deleteById(int id);
    @Insert
    void insert(Message message);
    @Insert
    void insertAll(Message... messages);
    @Query("DELETE FROM message WHERE timestamp < :date")
    void deleteOldMessages(long date);
    @Query("SELECT * FROM message WHERE id_conversation = :convID ORDER BY timestamp ASC")
    Message[] getAllMessagesByConvId(int convID);
}
