package uqac.dim.crypturmess.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "User")
public class UserClientSide extends User {
    public UserClientSide(){
        super();
    }

    @Ignore
    public UserClientSide(String id, String nickname, String username) {
        super(id, nickname);
        setUsername(username);
    }
    @ColumnInfo(name = "username")
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
