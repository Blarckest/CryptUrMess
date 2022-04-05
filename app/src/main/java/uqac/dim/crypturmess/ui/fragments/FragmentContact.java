package uqac.dim.crypturmess.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;
import androidx.room.RoomDatabase;
import androidx.lifecycle.*;

import java.util.List;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.Conversation;
import uqac.dim.crypturmess.model.entity.Message;
import uqac.dim.crypturmess.model.entity.User;

public class FragmentContact extends ListFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ERROR : java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(getActivity(), android.R.layout.list_content, AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).userDao().getFriends());
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {

    }
}
