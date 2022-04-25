package uqac.dim.crypturmess.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaDataSource;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.databaseAccess.firebase.IDatabaseHelper;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.services.AppService;
import uqac.dim.crypturmess.services.Restarter;
import uqac.dim.crypturmess.ui.UserBGManager;
import uqac.dim.crypturmess.ui.fragments.FragmentContact;
import uqac.dim.crypturmess.utils.auth.FirebaseAuthManager;
import uqac.dim.crypturmess.utils.auth.IAuthManager;

public class ContactActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authStateListener;
    private IAuthManager authManager=new FirebaseAuthManager();
    private IDatabaseHelper dbHelper=new FirebaseHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (authManager.getCurrentUser() != null) {
            authManager.getCurrentUser().reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (authManager.getCurrentUser() == null) {
                        Log.d("DIM", "updateCurrentUser:success");
                        startActivity(new Intent(ContactActivity.this, LoginActivity.class));
                        finish();
                    }
                    else {
                        Log.d("DIM", "onAuthStateChanged: user is logged in (" + authManager.getCurrentUser().getUid() + ")");
                        if (!isMyServiceRunning(AppService.class)) {
                            startService(new Intent(this, AppService.class));
                        }
                        if (getIntent().getExtras()!=null && getIntent().getExtras().size() >=2) {
                            Intent intent = new Intent(this, MessagesActivity.class);
                            intent.putExtra("ID_USER", getIntent().getExtras().getString("ID_USER"));
                            intent.putExtra("ID_CONVERSATION", getIntent().getExtras().getInt("ID_CONVERSATION"));
                            startActivity(intent);

                        }
                        String nickname = new SharedPreferencesHelper().getValue(R.string.nicknameSharedPref);
                        ((TextView)findViewById(R.id.c_user_letter_contact)).setText(new String(Character.toChars(nickname.codePointAt(0))).toUpperCase());
                        ((FrameLayout)findViewById(R.id.c_user_bg_contact)).setBackground(new UserBGManager().getBackgroundByUserName(nickname));
                        ((TextView)findViewById(R.id.c_text_user)).setText(nickname);
                        dbHelper.pushRSAPublicKey();
                    }
                }
            });
            setContentView(R.layout.activity_main);
            ((EditText)findViewById(R.id.c_edittext_recherche)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    ((FragmentContact)getSupportFragmentManager().findFragmentById(R.id.c_scrollview_fragment)).filter(charSequence);
                }


                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        else {
            Log.d("DIM", "onAuthStateChanged: user is logged out");
            startActivity(new Intent(ContactActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    public void addContact(View view) {
        Intent intent = new Intent(ContactActivity.this, AddContactActivity.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(ContactActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }
}