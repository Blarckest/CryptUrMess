package uqac.dim.crypturmess.databaseAccess.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import uqac.dim.crypturmess.model.entity.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Delete
    void delete(User user);
    @Query("SELECT * FROM user WHERE id!=0")
    User[] getFriends();
    @Query("SELECT * FROM user WHERE id=:id")
    User getUserById(int id);
}
