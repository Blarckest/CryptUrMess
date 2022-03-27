package uqac.dim.crypturmess.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import uqac.dim.crypturmess.R;
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
         authStateListener= firebaseAuth -> {
             if (authManager.getCurrentUser() != null) {
                 Log.d("DIM", "onAuthStateChanged: user is logged in (" + authManager.getCurrentUser().getUid() + ")");
                 startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
        //todo lauch register activity
    }

    public void clickOnLogin(View view) {
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        if(authManager.validateEmailAndPassword(email,password)== ValidationError.NO_ERROR){
            authManager.signIn(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success: for uid:"+ authManager.getCurrentUser().getUid());
                }
                else {
                    //todo display error to user
                }
            });
        }
        else{
            //todo display error to user
        }
    }

    public void clickOnForgotPassword(View view) {
       if (new EmailValidator().isValid(((EditText) findViewById(R.id.email)).getText().toString())){
           authManager.resetPassword(((EditText) findViewById(R.id.email)).getText().toString()).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   Log.d(TAG, "Email sent to "+((EditText) findViewById(R.id.email)).getText().toString());
               }
               else {
                   Log.d(TAG, "Failed to send email");
                   //todo display error to user
               }
           });
       }
       else{
           //todo display error to user
       }
    }
}
