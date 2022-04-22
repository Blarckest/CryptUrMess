package uqac.dim.crypturmess.databaseAccess.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import uqac.dim.crypturmess.model.entity.Message;

@Dao
public abstract class MessageDao extends Observable {
    private ArrayList<Observer> observers = new ArrayList<>();
    @Delete
    public abstract void delete(Message message);
    @Query("DELETE FROM Message WHERE id_message = :id")
    public abstract void deleteById(int id);
    @Insert
    public abstract void insert(Message message);
    @Insert
    public abstract void insertAll(Message... messages);
    @Query("DELETE FROM message WHERE timestamp < :date") //J-1
    public abstract void deleteOldMessages(long date);
    @Query("SELECT * FROM message WHERE id_conversation = :convID ORDER BY timestamp ASC")
    public abstract Message[] getAllMessagesByConvId(int convID);
    @Query("SELECT message_content FROM message WHERE id_conversation = :convID ORDER BY timestamp DESC LIMIT 1")
    public abstract String getLastMessageFromConv(int convID);
    @Transaction
    public void insertAndNotify(Message message){
        insert(message);
        for (Observer observer : observers) {
            observer.update(this, message);
        }
    }
    public void addObserver(Observer observer){
        observers.add(observer);
    }

}
