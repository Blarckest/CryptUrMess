package uqac.dim.crypturmess.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.Message;
import uqac.dim.crypturmess.ui.adapter.MessageListAdapter;
import uqac.dim.crypturmess.utils.crypter.ICrypter;
import uqac.dim.crypturmess.utils.crypter.RSA.RSACrypter;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Bundle extras = getIntent().getExtras();
        int id = 0;
        if (extras != null) {
            id = extras.getInt("ID_CONVERSATION");
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.m_recycle);
        MessageListAdapter adapter = new MessageListAdapter(this, AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).messageDao().getAllMessagesByConvId(id));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Send a message
     * @param view
     */
    public void onSendMessage(View view) {
        ICrypter crypter = new RSACrypter();
        //String message_content = ((EditText) findViewById(R.id.c_edittext_message)).getText().toString();
        int id_conversation = getIntent().getExtras().getInt("ID_CONVERSATION");
        //Message message = new Message(message_content, id_conversation);
        //CryptedMessage cryptedMessage = new CryptedMessage(message, crypter, false);
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        //firebaseHelper.sendMessage(cryptedMessage);
    }

}