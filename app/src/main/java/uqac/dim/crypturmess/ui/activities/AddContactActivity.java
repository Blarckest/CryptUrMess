package uqac.dim.crypturmess.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.Conversation;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.services.AppService;

public class AddContactActivity extends AppCompatActivity {
    private FirebaseHelper firebaseHelper=new FirebaseHelper();
    private AppLocalDatabase appLocalDatabase=AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
    private ProgressDialog dialog=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
    }

    public void add(View view) {
        dialog.show();
        String idUser = ((EditText)findViewById(R.id.adc_edit)).getText().toString();
        String username= ((EditText)findViewById(R.id.adc_username)).getText().toString();
        firebaseHelper.getUser(idUser).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);
                if (user != null) {
                    UserClientSide friend = new UserClientSide(user, username);
                    appLocalDatabase.userDao().insert(friend);
                    appLocalDatabase.conversationDao().insert(new Conversation(friend.getIdUser()));
                    dialog.dismiss();
                    dialog.hide();
                    finish();
                }
                else{
                    addError(R.string.error_contact);
                }
            }
            else {
                addError(R.string.error_server);
            }
        }).addOnSuccessListener(task -> {
            firebaseHelper.getRSAPublicKeyOfUser(idUser).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    String publicKey = task1.getResult().getValue(String.class);
                    appLocalDatabase.userDao().addRSAPublicKeyToUser(idUser, publicKey);
                }
                else {
                    appLocalDatabase.userDao().addRSAPublicKeyToUser(idUser, "");
                }
            });
        });
    }

    public void addError(int stringErrorId){
        Toast toast = Toast.makeText(AddContactActivity.this, stringErrorId, Toast.LENGTH_LONG);
        toast.show();
        dialog.dismiss();
        dialog.hide();
    }
}
