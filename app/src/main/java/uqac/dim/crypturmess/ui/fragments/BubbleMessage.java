package uqac.dim.crypturmess.ui.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.utils.crypter.ICrypter;
import uqac.dim.crypturmess.utils.crypter.RSA.RSACrypter;

public class BubbleMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bubble_message);

    }

}