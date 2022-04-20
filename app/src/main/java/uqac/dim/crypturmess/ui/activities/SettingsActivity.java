package uqac.dim.crypturmess.ui.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.services.AppService;
import uqac.dim.crypturmess.ui.UserBGManager;
import uqac.dim.crypturmess.utils.auth.FirebaseAuthManager;
import uqac.dim.crypturmess.utils.auth.IAuthManager;

public class SettingsActivity extends AppCompatActivity {
    private AppLocalDatabase db = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
    private String loginID = new SharedPreferencesHelper().getValue(R.string.userIDSharedPref);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String nickname = new SharedPreferencesHelper().getValue(R.string.nicknameSharedPref);
        setContentView(R.layout.activity_settings);
        ((TextView)findViewById(R.id.s_text_user)).setText(nickname);
        ((TextView)findViewById(R.id.s_id_user)).setText(new SharedPreferencesHelper().getValue(R.string.userIDSharedPref));
        ((TextView)findViewById(R.id.s_user_letter_contact)).setText(new String(Character.toChars(nickname.codePointAt(0))).toUpperCase());

        ((FrameLayout)findViewById(R.id.s_user_bg)).setBackground(new UserBGManager().getBackgroundByUserName(nickname));


        //frameLayoutBG.setBackground();
    }

    public void copyID(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("loginID", loginID);
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(SettingsActivity.this, R.string.id_copied, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}