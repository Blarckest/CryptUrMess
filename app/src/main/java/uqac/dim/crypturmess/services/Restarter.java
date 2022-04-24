package uqac.dim.crypturmess.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("DIM", "Service tried to stop,restarted");
        context.startForegroundService(new Intent(context, AppService.class));
    }
}
