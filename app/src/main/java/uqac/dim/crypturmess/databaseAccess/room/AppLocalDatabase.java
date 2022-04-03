package uqac.dim.crypturmess.databaseAccess.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import uqac.dim.crypturmess.databaseAccess.room.dao.ConversationDao;
import uqac.dim.crypturmess.databaseAccess.room.dao.MessageDao;
import uqac.dim.crypturmess.databaseAccess.room.dao.UserDao;
import uqac.dim.crypturmess.model.entity.Conversation;
import uqac.dim.crypturmess.model.entity.Message;
import uqac.dim.crypturmess.model.entity.User;

@Database(entities={User.class, Message.class, Conversation.class}, version=1)
public abstract class AppLocalDatabase extends RoomDatabase {
    AppLocalDatabase(){}
    private AppLocalDatabase instance;
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
    public abstract ConversationDao conversationDao();
    public AppLocalDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppLocalDatabase.class, "crypturmess_database").build();
        }
        return instance;
    }
}
