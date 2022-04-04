package uqac.dim.crypturmess.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "User")
public class UserClientSide extends User {
    public UserClientSide(){
        super();
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
