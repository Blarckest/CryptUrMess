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
import uqac.dim.crypturmess.model.entity.UserClientSide;

@Database(entities={UserClientSide.class, Message.class, Conversation.class}, version=1, exportSchema = false)
public abstract class AppLocalDatabase extends RoomDatabase {
    private static AppLocalDatabase instance;
    public abstract UserDao userDao();
    public abstract MessageDao messageDao();
    public abstract ConversationDao conversationDao();
    public static AppLocalDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppLocalDatabase.class, "crypturmess_database").build();
        }
        return instance;
    }
}
