package uqac.dim.crypturmess.services;

import android.Manifest;
import android.app.IntentService;
import android.app.NotificationManager;
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
import java.util.Arrays;
import java.util.HashMap;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.databaseAccess.firebase.IDatabaseHelper;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.Conversation;
import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.Message;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.ui.activities.ContactActivity;
import uqac.dim.crypturmess.ui.activities.MessagesActivity;
import uqac.dim.crypturmess.ui.notifications.Notifier;
import uqac.dim.crypturmess.utils.crypter.AES.AESDecrypter;
import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.IDecrypter;
import uqac.dim.crypturmess.utils.crypter.RSA.RSADecrypter;
import uqac.dim.crypturmess.utils.looper.DeleteMessagesLooper;


public class AppService extends IntentService {
    private IBinder binder = new LocalBinder();
    private AppLocalDatabase database = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
    private DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference();
    private SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private ArrayList<UserClientSide> users = new ArrayList<>(Arrays.asList(database.userDao().getFriends()));
    private IDecrypter RSADecrypter = new RSADecrypter();
    private IDecrypter AESDecrypter = new AESDecrypter();
    private Notifier notifier = null;
    private DeleteMessagesLooper deleteMessagesLooper = new DeleteMessagesLooper();
    private IDatabaseHelper fbHelper = new FirebaseHelper();
    private int hashLastMessage = 0;

    public AppService() {
        super("AppService");
    }

    public AppService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notifier = new Notifier(this);
        startForeground(1,notifier.appNotification());
        for (UserClientSide user : users) {
            registerUser(user);
        }
        new Thread(deleteMessagesLooper).start();
        firebaseDB.child("messages").child(sharedPreferencesHelper.getValue(R.string.userIDSharedPref)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = new User(snapshot.getKey(), "");
                if (!users.contains(user) || !users.containsAll(Arrays.asList(database.userDao().getFriends()))) {
                    fbHelper.getUser(snapshot.getKey()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User userTask = task.getResult().getValue(User.class);
                            if (userTask != null) {
                                UserClientSide friend = new UserClientSide(userTask, "");
                                try {
                                    database.userDao().insertAndNotify(friend);
                                    database.conversationDao().insert(new Conversation(friend.getIdUser()));
                                } catch (Exception e) {
                                    Log.e("DIM", e.getMessage());
                                }
                            }
                        }
                    }).addOnSuccessListener(task -> {
                        fbHelper.getRSAPublicKeyOfUser(user.getIdUser()).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                String publicKey = task1.getResult().getChildren().iterator().next().getValue(String.class);
                                database.userDao().addRSAPublicKeyToUser(user.getIdUser(), publicKey);
                            } else {
                                database.userDao().addRSAPublicKeyToUser(user.getIdUser(), "");
                            }
                        }).addOnSuccessListener(task2 -> {
                            if (snapshot.getValue() != null) {
                                DatabaseReference ref = snapshot.getChildren().iterator().next().getRef();
                                ref.get().addOnSuccessListener(taskGet -> {
                                    if (task2.getValue() != null) {
                                        CryptedMessage msgCrypte = taskGet.getValue(CryptedMessage.class);
                                        Message msg = null;
                                        if (msgCrypte.getAlgorithm() != null) {
                                            if (msgCrypte.getAlgorithm().equals(Algorithm.RSA))
                                                msg = new Message(msgCrypte, RSADecrypter, true, true);
                                            else if (msgCrypte.getAlgorithm().equals(Algorithm.AES))
                                                msg = new Message(msgCrypte, AESDecrypter, true, true);
                                            else
                                                Log.e("DIM", "onChildAdded: Bad algorithm while receiving");
                                            int hash=msg.hashCode();
                                            if (msg != null && hash!=hashLastMessage) {
                                                Conversation conv = database.conversationDao().getConversationById(msg.getIdConversation());
                                                UserClientSide userClientSide = database.userDao().getUserById(conv.getIdCorrespondant());
                                                Intent intent = new Intent(CrypturMessApplication.getContext(), ContactActivity.class);
                                                intent.putExtra("ID_CONVERSATION", conv.getIdConversation());
                                                intent.putExtra("ID_USER", userClientSide.getIdUser());
                                                String end;
                                                if(msg.getMessage().length()>50)
                                                    end="...";
                                                else
                                                    end="";
                                                notifier.sendNotification(userClientSide.getIdUser(), msg.getMessage().substring(0, Math.min(msg.getMessage().length(), 50)) + end, intent);
                                                users.add(userClientSide);
                                                registerUser(userClientSide);
                                                hashLastMessage = hash;
                                            }
                                        }
                                    }
                                });
                                ref.removeValue();
                            }
                        });
                    });
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
        });
    }

    @Override
    public void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DIM", "onStartCommand: Service");
        return START_STICKY;
    }

    protected void registerUser(UserClientSide user){
        DatabaseReference ref = firebaseDB.child("messages").child(sharedPreferencesHelper.getValue(R.string.userIDSharedPref)).child(user.getIdUser());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue() != null) {
                    CryptedMessage msgCrypte = snapshot.getValue(CryptedMessage.class);
                    Message msg = null;
                    if (msgCrypte.getAlgorithm() != null) {
                        if (msgCrypte.getAlgorithm().equals(Algorithm.RSA))
                            msg = new Message(msgCrypte, RSADecrypter, true, true);
                        else if (msgCrypte.getAlgorithm().equals(Algorithm.AES))
                            msg = new Message(msgCrypte, AESDecrypter, true, true);
                        else
                            Log.e("DIM", "onChildAdded: Bad algorithm while receiving");
                        int hash=msg.hashCode();
                        if (msg != null && hash!=hashLastMessage) {
                            Conversation conv = database.conversationDao().getConversationById(msg.getIdConversation());
                            UserClientSide user = database.userDao().getUserById(conv.getIdCorrespondant());
                            Intent intent = new Intent(CrypturMessApplication.getContext(), ContactActivity.class);
                            intent.putExtra("ID_CONVERSATION", conv.getIdConversation());
                            intent.putExtra("ID_USER", user.getIdUser());
                            String end;
                            if(msg.getMessage().length()>50)
                                end="...";
                            else
                                end="";
                            notifier.sendNotification(user.getIdUser(), msg.getMessage().substring(0, Math.min(msg.getMessage().length(), 50)) + end, intent);
                            hashLastMessage=hash;
                        }
                    }
                }
                snapshot.getRef().removeValue();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //onChildAdded(snapshot,previousChildName);
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
        });

        firebaseDB.child("keys").child("RSA").child(user.getIdUser()).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                database.userDao().addRSAPublicKeyToUser(snapshot.getRef().getParent().getKey(), snapshot.getValue(String.class));
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
        });
    }

    public class LocalBinder extends Binder {
        public AppService getService() {
            return AppService.this;
        }
    }
}