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
import uqac.dim.crypturmess.model.entity.Conversation;
import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.Message;
import uqac.dim.crypturmess.ui.adapter.MessageListAdapter;
import uqac.dim.crypturmess.utils.crypter.ICrypter;
import uqac.dim.crypturmess.utils.crypter.RSA.RSACrypter;

public class MessagesActivity extends AppCompatActivity {

    private int id;
    private RecyclerView recyclerView;
    private FirebaseHelper firebaseHelper = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Bundle extras = getIntent().getExtras();
        id = 0;
        if (extras != null) {
            id = extras.getInt("ID_CONVERSATION");
        }


        recyclerView = (RecyclerView) findViewById(R.id.m_recycle);
        MessageListAdapter adapter = new MessageListAdapter(this, AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).messageDao().getAllMessagesByConvId(id));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ((RecyclerView)findViewById(R.id.m_recycle)).scrollToPosition(adapter.getItemCount() - 1);
    }

    /**
     * Send a message
     * @param view
     */
    public void onSendMessage(View view) {
        ICrypter crypter = new RSACrypter();
        int id_conversation = getIntent().getExtras().getInt("ID_CONVERSATION");
        String strMessage = ((EditText) findViewById(R.id.m_enter_message)).getText().toString();
        Message message = new Message(strMessage, id_conversation);
        AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).messageDao().insert(message);

        CryptedMessage cryptedMessage = new CryptedMessage(message, crypter, false);
        firebaseHelper.sendMessage(cryptedMessage);


        MessageListAdapter adapter = new MessageListAdapter(this, AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).messageDao().getAllMessagesByConvId(id));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ((EditText) findViewById(R.id.m_enter_message)).setText("");
        ((RecyclerView)findViewById(R.id.m_recycle)).scrollToPosition(adapter.getItemCount() - 1);
    }

}