package uqac.dim.crypturmess.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.utils.auth.FirebaseAuthManager;
import uqac.dim.crypturmess.utils.auth.IAuthManager;

public class Restarter extends BroadcastReceiver {
    private IAuthManager authManager=new FirebaseAuthManager();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("DIM", "Service tried to stop,restarted");
        if (authManager.getCurrentUser()!=null) {
            authManager.getCurrentUser().reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (authManager.getCurrentUser() != null) {
                        context.startForegroundService(new Intent(context, AppService.class));
                    }
                }
            });
        }
    }
}
