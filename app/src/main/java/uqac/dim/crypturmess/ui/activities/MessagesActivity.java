package uqac.dim.crypturmess.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.SharedPreferencesHelper;
import uqac.dim.crypturmess.databaseAccess.firebase.FirebaseHelper;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.CryptedMessage;
import uqac.dim.crypturmess.model.entity.Message;
import uqac.dim.crypturmess.ui.adapter.MessageListAdapter;
import uqac.dim.crypturmess.ui.notifications.Notifier;
import uqac.dim.crypturmess.utils.crypter.AES.AESDecrypter;
import uqac.dim.crypturmess.utils.crypter.Algorithm;
import uqac.dim.crypturmess.utils.crypter.ICrypter;
import uqac.dim.crypturmess.utils.crypter.IDecrypter;
import uqac.dim.crypturmess.utils.crypter.RSA.RSACrypter;
import uqac.dim.crypturmess.utils.crypter.RSA.RSADecrypter;

public class MessagesActivity extends AppCompatActivity {

    private int id;
    private String id_user;
    private RecyclerView recyclerView;
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private DatabaseReference firebaseDB = FirebaseDatabase.getInstance().getReference();
    private SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper();
    private IDecrypter RSADecrypter = new RSADecrypter();
    private IDecrypter AESDecrypter = new AESDecrypter();
    private MessageListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Bundle extras = getIntent().getExtras();
        id = 0;
        if (extras != null) {
            id = extras.getInt("ID_CONVERSATION");
            id_user = extras.getString("ID_USER");
        }

        recyclerView = (RecyclerView) findViewById(R.id.m_recycle);
        adapter = new MessageListAdapter(this, AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).messageDao().getAllMessagesByConvId(id));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ((RecyclerView) findViewById(R.id.m_recycle)).scrollToPosition(adapter.getItemCount() - 1);

        firebaseDB.child("messages").child(sharedPreferencesHelper.getValue(R.string.userIDSharedPref)).child(id_user).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot.getValue() != null) {
                            CryptedMessage msgCrypte = snapshot.getValue(CryptedMessage.class);
                            Message msg = null;
                            if (msgCrypte.getAlgorithm() != null) {
                                if (msgCrypte.getAlgorithm().equals(Algorithm.RSA)) {
                                    msg = new Message(msgCrypte, RSADecrypter, false, true);
                                }
                                else if (msgCrypte.getAlgorithm().equals(Algorithm.AES)) {
                                    msg = new Message(msgCrypte, AESDecrypter, false, true);
                                }
                                else
                                    Log.e("DIM", "onChildAdded: Bad algorithm while receiving");

                                adapter.addMessage(msg);
                                ((RecyclerView) findViewById(R.id.m_recycle)).scrollToPosition(adapter.getItemCount() - 1);
                            }

                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Notifier.unblockNotifForUser(id_user);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Notifier.blockNotifForUser(id_user);
    }

    /**
     * Send a message
     *
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

        adapter.addMessage(message);

        ((EditText) findViewById(R.id.m_enter_message)).setText("");
        ((RecyclerView) findViewById(R.id.m_recycle)).scrollToPosition(adapter.getItemCount() - 1);
    }

    /**
     * Get current location of the phone
     */
    public void onGetLocation(View view) {
        setUserLocation();
    }

    public Pair<Double, Double> getUserLocation() {
        // Creating an instance of LocationManager in the context of LOCATION_SERVICE.
        //Location currentLocation = null;
        LocationManager locationManager;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check that if the GPS and Network are available or not and if both are available then we use one with greater accuracy.
        boolean hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        String provider;
        if (hasGps)
            provider = LocationManager.NETWORK_PROVIDER;
        else
            provider = LocationManager.GPS_PROVIDER;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return null;
        }
        try {
            Location location = locationManager.getLastKnownLocation(provider);
            Log.i("DIM", Double.toString(location.getLatitude()) + " " + Double.toString(location.getLongitude()));
            return new Pair<Double, Double>(location.getLatitude(), location.getLongitude());
        }
        catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Impossible de récupérer les coordonnées GPS", Toast.LENGTH_LONG).show();
            return new Pair<Double, Double>(null, null);
        }
    }

    public void setUserLocation() {
        Pair<Double, Double> pcoord = getUserLocation();
        if (pcoord.first != null && pcoord.second != null) {
            String coord = Double.toString(pcoord.first) + "," + Double.toString(pcoord.second);
            ((EditText) findViewById(R.id.m_enter_message)).setText(coord);
            Log.i("LOCATION", coord);
        }
    }

    public void openInMaps(View view) {
        if (view.getTag() != null) {
            // on vérifie que le format de coordonnes est sous la bonne forme
            Pattern p = Pattern.compile("[+-]?[0-9]{2}\\.[0-9]*,[+-]?[0-9]{2}\\.[0-9]*");
            Matcher m;
            m = p.matcher(view.getTag().toString());
            if (m.find() && view.getTag() != null) {
                // on ouvre maps
                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + view.getTag());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // On relance la méthode de réalisation de l'action. Cette fois, on a l'autorisation.
                    setUserLocation();

                } else {
                   Toast.makeText(getApplicationContext(), "Impossible de récupérer la localisation actuellement", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
