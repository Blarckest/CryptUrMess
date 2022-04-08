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

import androidx.appcompat.app.AppCompatActivity;

import uqac.dim.crypturmess.R;

public class Notifier{
    private static String CHANNEL_ID = "id_01";
    private static String CHANNEL_NAME = "notifier";
    private static String CHANNEL_DESCRIPTION = "notif";
    private static int NOTIFICATION_ID = 42;
    private NotificationManager notifManager;
    Context context;


    public Notifier(Context context){
        this.context = context;
        createNotificationChannel();
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            notifManager = context.getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String title, String text) {
        Notification n  = new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
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
