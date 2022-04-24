package uqac.dim.crypturmess.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.HashSet;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;

public class Notifier{
    private static String CHANNEL_ID = "id_01";
    private static String CHANNEL_ID_CONSTANT_NOTIF = "id_02";
    private static String CHANNEL_NAME = "messages";
    private static String CHANNEL_NAME_CONSTANT_NOTIF = "run_notif";
    private static String CHANNEL_DESCRIPTION = "notif";
    private static String CHANNEL_DESCRIPTION_CONSTANT_NOTIF = "run_notif";
    private static int NOTIFICATION_ID = 42;
    private static int NOTIFICATION_ID_CONSTANT_NOTIF = 43;
    private NotificationManager notifManager;
    private Context context;
    private AppLocalDatabase db=AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
    private static HashSet<String> blacklistedUser=new HashSet<>();


    public Notifier(Context context){
        this.context = context;
        createNotificationChannel();
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notifManager = context.getSystemService(NotificationManager.class);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_CONSTANT_NOTIF, CHANNEL_NAME_CONSTANT_NOTIF, importance);
            channel2.setDescription(CHANNEL_DESCRIPTION_CONSTANT_NOTIF);
            notifManager.createNotificationChannel(channel);
            notifManager.createNotificationChannel(channel2);
        }
    }

    public void sendNotification(String UID, String text, @Nullable Intent intent){
       if (!blacklistedUser.contains(UID)){
           PendingIntent pendingIntent = null;
           if (intent != null) {
               pendingIntent = PendingIntent.getActivity(CrypturMessApplication.getContext(), 0, intent, 0);
           }
           UserClientSide user=db.userDao().getUserById(UID);
           String title=user.getNickname() + "(" + user.getUsername() + ")";
           Notification n  = new Notification.Builder(context, CHANNEL_ID)
                   .setContentTitle(title)
                   .setContentText(text)
                   .setSmallIcon(R.mipmap.ic_cryturmess)
                   .setAutoCancel(true)
                   .setContentIntent(pendingIntent)
                   .setColor(ContextCompat.getColor(context, R.color.green))
                   //.setFullScreenIntent(pIntent, true)
                   //.setStyle(new Notification.BigTextStyle().bigText(extra_nom))
                   .build();
           try{
               notifManager.notify(NOTIFICATION_ID, n);
           }
           catch(Exception e){
               Log.i("ERROR", e.getMessage(), e);
           }
       }
    }

    public Notification appNotification(){
        Notification n  = new Notification.Builder(context, CHANNEL_ID_CONSTANT_NOTIF)
                .setContentTitle("CrypturMess")
                .setContentText("CrypturMess is running")
                .setSmallIcon(R.mipmap.ic_cryturmess)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.green))
                .build();
        return n;
    }

    public void cancelNotification(int id){
        notifManager.cancel(id);
    }

    public static void blockNotifForUser(String UID){
        blacklistedUser.add(UID);
    }

    public static void unblockNotifForUser(String UID){
        blacklistedUser.remove(UID);
    }
}
