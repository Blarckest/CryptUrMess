package uqac.dim.crypturmess.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import uqac.dim.crypturmess.R;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("ID_USER");
            ((TextView)findViewById(R.id.m_ok)).setText(id);
        }
    }

}