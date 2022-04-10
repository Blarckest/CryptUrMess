package uqac.dim.crypturmess.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.Conversation;
import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.Message;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.ui.notifications.Notifier;
import uqac.dim.crypturmess.utils.crypter.AES.AESDecrypter;
import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.IDecrypter;
import uqac.dim.crypturmess.utils.crypter.RSA.RSADecrypter;
import uqac.dim.crypturmess.utils.looper.DeleteMessagesLooper;


public class AppService extends Service {
    private IBinder binder=new LocalBinder();
    private AppLocalDatabase database= AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
    private DatabaseReference firebaseDB=FirebaseDatabase.getInstance().getReference();
    private SharedPreferencesHelper sharedPreferencesHelper=new SharedPreferencesHelper();
    private UserClientSide[] users=database.userDao().getFriends();
    private IDecrypter RSADecrypter = new RSADecrypter();
    private IDecrypter AESDecrypter = new AESDecrypter();
    private ArrayList<ChildEventListener> listeners= new ArrayList<>();
    private Notifier notifier=null;
    private DeleteMessagesLooper deleteMessagesLooper=new DeleteMessagesLooper();

    @Override
    public void onCreate() {
        super.onCreate();
        notifier=new Notifier(this);
        listeners.add(firebaseDB.child("messages").child(sharedPreferencesHelper.getValue(R.string.userIDSharedPref)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CryptedMessage msgCrypte= snapshot.getValue(CryptedMessage.class);
                Message msg=null;
                if(msgCrypte.getAlgorithm()== Algorithm.RSA)
                    msg=new Message(msgCrypte,RSADecrypter,true);
                else if(msgCrypte.getAlgorithm()== Algorithm.AES)
                    msg=new Message(msgCrypte,AESDecrypter,true);
                else
                    Log.e("DIM", "onChildAdded: Bad algorithm while receiving");
                snapshot.getRef().removeValue();
                if(msg!=null) {
                    Conversation conv = database.conversationDao().getConversationById(msg.getIdConversation());
                    UserClientSide user = database.userDao().getUserById(conv.getIdCorrespondant());
                    notifier.sendNotification(user.getNickname()+"("+user.getUsername()+")",msg.getMessage().substring(0,msg.getMessage().length()>50?50:msg.getMessage().length())+"...");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
        for (UserClientSide user: users) {
            listeners.add(firebaseDB.child("keys").child("RSA").child(user.getIdUser()).addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    database.userDao().addRSAPublicKeyToUser(user.getIdUser(),snapshot.getValue(String.class));
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            }));
        }
        new Thread(deleteMessagesLooper).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (ChildEventListener listener: listeners) {
            firebaseDB.addChildEventListener(listener);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DIM", "onStartCommand: Service");
        return START_STICKY;
    }

    public class LocalBinder extends Binder {
        public AppService getService() {
            return AppService.this;
        }
    }
}