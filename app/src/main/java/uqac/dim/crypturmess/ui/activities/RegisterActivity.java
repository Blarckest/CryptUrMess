package uqac.dim.crypturmess.ui.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.databaseAccess.firebase.IDatabaseHelper;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.utils.auth.FirebaseAuthManager;
import uqac.dim.crypturmess.utils.auth.IAuthManager;
import uqac.dim.crypturmess.utils.auth.ValidationError;

public class RegisterActivity extends AppCompatActivity{
    private IAuthManager authManager=new FirebaseAuthManager();
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseHelper fbHelper = new FirebaseHelper();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         authStateListener= firebaseAuth -> {
             if (authManager.getCurrentUser() != null) {
                 Log.d("DIM", "onAuthStateChanged: user is logged in (" + authManager.getCurrentUser().getUid() + ")");
                 startActivity(new Intent(RegisterActivity.this, ContactActivity.class));
                 finish();
             }
         };
    }


    @Override
    protected void onStop(){
        super.onStop();
        if (authStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }
    }

    public void clickOnRegister(View view) {
        String email = ((EditText) findViewById(R.id.r_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.r_password)).getText().toString();
        String nickname = ((EditText) findViewById(R.id.r_login)).getText().toString();
        if(authManager.validateEmailAndPassword(email,password)== ValidationError.NO_ERROR){
            authManager.createUser(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.i("DIM", "ok boomer");
                    Log.d("DIM", "create user :success: for uid:"+ authManager.getCurrentUser().getUid());
                    User user=new User(authManager.getCurrentUser().getUid(),nickname);
                    fbHelper.saveUser(user);
                    Intent intent = new Intent(RegisterActivity.this, ContactActivity.class);
                    pushKeys();
                    SharedPreferencesHelper sharedPreferencesHelper= new SharedPreferencesHelper();
                    sharedPreferencesHelper.setValue(R.string.userIDSharedPref, authManager.getCurrentUser().getUid());
                    sharedPreferencesHelper.setValue(R.string.nicknameSharedPref, nickname);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.d(TAG, "create user:error");
                    Toast toast = Toast.makeText(RegisterActivity.this, R.string.error_login, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
        else{
            Log.d(TAG, "create user:error");
            Toast toast = Toast.makeText(RegisterActivity.this, R.string.error_psw_mail, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void pushKeys(){
        IDatabaseHelper dbHelper=new FirebaseHelper();
        dbHelper.pushRSAPublicKey();
    }
}
