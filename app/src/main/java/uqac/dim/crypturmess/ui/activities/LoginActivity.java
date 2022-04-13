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
import com.google.firebase.auth.FirebaseUser;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.databaseAccess.firebase.IDatabaseHelper;
import uqac.dim.crypturmess.ui.notifications.Notifier;
import uqac.dim.crypturmess.utils.auth.FirebaseAuthManager;
import uqac.dim.crypturmess.utils.auth.IAuthManager;
import uqac.dim.crypturmess.utils.auth.ValidationError;
import uqac.dim.crypturmess.utils.validator.EmailValidator;

public class LoginActivity extends AppCompatActivity {
    private IAuthManager authManager=new FirebaseAuthManager();
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onStart() {
        if (authManager.getCurrentUser() != null) {
            authManager.getCurrentUser().reload().addOnCompleteListener(task -> {
                if (task.isSuccessful() && authManager.getCurrentUser() == null) {
                    Log.d("DIM", "updateCurrentUser:success");
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                }
            });
            Log.d("DIM", "onAuthStateChanged: user is logged in (" + authManager.getCurrentUser().getUid() + ")");
            startActivity(new Intent(LoginActivity.this, ContactActivity.class));
            finish();
        }
        else {
            Log.d("DIM", "onAuthStateChanged: user is logged out");
        }
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    public void clickOnRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        /*intent.putExtra(EXTRA_NOM, nom);
        intent.putExtra(EXTRA_URL, url);*/
        startActivity(intent);
    }

    public void clickOnLogin(View view) {
        String email = ((EditText) findViewById(R.id.l_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.l_password)).getText().toString();
        if(authManager.validateEmailAndPassword(email,password)== ValidationError.NO_ERROR){
            authManager.signIn(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success: for uid:"+ authManager.getCurrentUser().getUid());
                    Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.d(TAG, "signInWithEmail:error");
                    Toast toast = Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
        else{
            Log.d(TAG, "signInWithEmail:error");
            Toast toast = Toast.makeText(LoginActivity.this, R.string.error_psw_mail, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void clickOnForgotPassword(View view) {
       if (new EmailValidator().isValid(((EditText) findViewById(R.id.l_email)).getText().toString())){
           authManager.resetPassword(((EditText) findViewById(R.id.l_email)).getText().toString()).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   Log.d(TAG, "Email sent to "+((EditText) findViewById(R.id.l_email)).getText().toString());
               }
               else {
                   Log.d(TAG, "Failed to send email");
                   Toast toast = Toast.makeText(LoginActivity.this, R.string.error_server, Toast.LENGTH_SHORT);
                   toast.show();
               }
           });
       }
       else{
           Log.d(TAG, "forgotPassword:error");
           Toast toast = Toast.makeText(LoginActivity.this, R.string.error_mail, Toast.LENGTH_SHORT);
           toast.show();
       }
    }

}
