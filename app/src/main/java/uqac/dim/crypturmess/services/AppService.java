package uqac.dim.crypturmess.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



public class AppService extends Service {
    private IBinder binder;

    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}