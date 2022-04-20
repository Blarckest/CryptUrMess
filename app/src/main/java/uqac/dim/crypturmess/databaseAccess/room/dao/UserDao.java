package uqac.dim.crypturmess.databaseAccess.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;

@Dao
public abstract class UserDao extends Observable {
    private ArrayList<Observer> observers = new ArrayList<>();
    @Insert
    public abstract void insert(UserClientSide user);
    @Delete
    public abstract void delete(UserClientSide user);
    @Query("SELECT * FROM user ORDER BY pseudo DESC")
    public abstract UserClientSide[] getFriends();
    @Query("SELECT * FROM user WHERE id_user=:id")
    public abstract UserClientSide getUserById(String id);
    @Query("UPDATE user SET RSA_public_key=:key WHERE id_user=:id ")
    public abstract void addRSAPublicKeyToUser(String id, String key);
    @Transaction
    public void insertAndNotify(UserClientSide user){
        insert(user);
        for (Observer observer : observers) {
            observer.update(this, user);
        }
    }
    public void addObserver(Observer observer){
        observers.add(observer);
    }
}
