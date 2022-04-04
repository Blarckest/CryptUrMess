package uqac.dim.crypturmess.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

/**
 * User class represent a user.
 */
@Entity(tableName = "User")
public class User {
    @ColumnInfo(name = "id")
    @PrimaryKey
    private String idUser;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "firstname")
    private String firstname;

    /**
     * Empty constructor
     */
    public User() { }

    /**
     * Constructor minimal
     */
    @Ignore
    public User(String username) {
        setUsername(username);
    }

    /**
     * Constructor full parameters
     */
    @Ignore
    public User(String id, String username, String firstname) {
        this(username);
        setFirstname(firstname);
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
