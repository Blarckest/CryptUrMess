package uqac.dim.crypturmess.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * User class represent a user.
 */
public class User {
    @ColumnInfo(name = "id_user")
    @PrimaryKey
    @NonNull
    private String idUser;
    @ColumnInfo(name = "pseudo")
    private String nickname;

    /**
     * Empty constructor
     */
    public User() { }

    /**
     * Constructor minimal
     */
    @Ignore
    public User(String nickname) {
        setNickname(nickname);
    }

    /**
     * Constructor full parameters
     */
    @Ignore
    public User(String id, String nickname) {
        this(nickname);
        this.idUser=id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
