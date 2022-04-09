package uqac.dim.crypturmess.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.services.AppService;

public class AddContactActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    public void add(View view) {
        String idUser = ((EditText)findViewById(R.id.adc_edit)).getText().toString();

        //TODO

        //cas d'erreur
        Toast toast = Toast.makeText(AddContactActivity.this, R.string.error_contact, Toast.LENGTH_SHORT);
        toast.show();
        //revenir à contact
        finish();
    }
}
