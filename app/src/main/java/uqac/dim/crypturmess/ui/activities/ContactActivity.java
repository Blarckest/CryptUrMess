package uqac.dim.crypturmess.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

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
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.services.AppService;
import uqac.dim.crypturmess.ui.UserBGManager;
import uqac.dim.crypturmess.ui.fragments.FragmentContact;
import uqac.dim.crypturmess.utils.auth.FirebaseAuthManager;
import uqac.dim.crypturmess.utils.auth.IAuthManager;

public class ContactActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authStateListener;
    private IAuthManager authManager=new FirebaseAuthManager();


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
                        startService(new Intent(this, AppService.class));
                        String nickname = new SharedPreferencesHelper().getValue(R.string.nicknameSharedPref);
                        ((TextView)findViewById(R.id.c_user_letter_contact)).setText(new String(Character.toChars(nickname.codePointAt(0))).toUpperCase());
                        ((ImageView)findViewById(R.id.c_img_icon_user)).setImageResource(new UserBGManager().getBackgroundByUserName(nickname));
                        ((TextView)findViewById(R.id.c_text_user)).setText(nickname);
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

    public void addContact(View view) {
        Intent intent = new Intent(ContactActivity.this, AddContactActivity.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(ContactActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}