package uqac.dim.crypturmess.databaseAccess.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import uqac.dim.crypturmess.model.entity.LocalUser;

@Dao
public interface LocalUserDao {
    @Query("DELETE FROM local_user")
    void deleteAll();
    @Insert
    void insert(LocalUser localUser);
    @Query("SELECT * FROM local_user WHERE id = :id")
    LocalUser get(int id);
}
