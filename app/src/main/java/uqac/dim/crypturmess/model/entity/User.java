package uqac.dim.crypturmess.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

/**
 * User class represent a user.
 */
@Entity(tableName = "User")
public class User {
    @ColumnInfo(name = "id")
    @PrimaryKey
    private int idUser;
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
    public User(String username) {
        setUsername(username);
    }

    /**
     * Constructor full parameters
     */
    public User(int id, String username, String firstname) {
        this(username);
        setFirstname(firstname);
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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
