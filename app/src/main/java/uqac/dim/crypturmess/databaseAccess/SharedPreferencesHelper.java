package uqac.dim.crypturmess.databaseAccess;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import uqac.dim.crypturmess.CrypturMessApplication;

public class SharedPreferencesHelper {
    private Context context =CrypturMessApplication.getInstance().getApplicationContext();
    private String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    public String getValue(String key){
        return context.getSharedPreferences(uid, Context.MODE_PRIVATE).getString(key,"");
    }
    public void setValue(String key,String value){
        context.getSharedPreferences(uid, Context.MODE_PRIVATE).edit().putString(key,value).apply();
    }
}
