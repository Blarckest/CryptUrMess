package uqac.dim.crypturmess.model.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "User")
public class UserClientSide extends User {
    public UserClientSide(){
        super();
    }

    @Ignore
    public UserClientSide(User user, @Nullable String username){
        super(user.getIdUser(), user.getNickname());
        setUsername(username);
    }

    @Ignore
    public UserClientSide(String id, String nickname, String username) {
        super(id, nickname);
        setUsername(username);
    }
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name="RSA_public_key")
    private String rsaPublicKey;

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
