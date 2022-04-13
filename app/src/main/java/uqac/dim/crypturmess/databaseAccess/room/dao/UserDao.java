package uqac.dim.crypturmess.databaseAccess.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;

@Dao
public interface UserDao {
    @Insert
    void insert(UserClientSide user);
    @Delete
    void delete(UserClientSide user);
    @Query("SELECT * FROM user ORDER BY pseudo DESC")
    UserClientSide[] getFriends();
    @Query("SELECT * FROM user WHERE id_user=:id")
    UserClientSide getUserById(String id);
    @Query("UPDATE user SET RSA_public_key=:key WHERE id_user=:id ")
    void addRSAPublicKeyToUser(String id, String key);
}
