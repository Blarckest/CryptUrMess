package uqac.dim.crypturmess;

import android.app.Application;
import android.content.Context;

public class CrypturMessApplication extends Application {
    private static CrypturMessApplication instance;

    public static CrypturMessApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
