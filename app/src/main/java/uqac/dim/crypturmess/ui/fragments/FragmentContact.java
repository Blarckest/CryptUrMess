package uqac.dim.crypturmess.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.Conversation;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.ui.activities.MessagesActivity;
import uqac.dim.crypturmess.ui.adapter.UserListAdapter;

public class FragmentContact extends ListFragment implements Observer {
    private AppLocalDatabase db = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
    private UserListAdapter adapter;
    private ArrayList<UserClientSide> users;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        UserClientSide user = (UserClientSide) getListAdapter().getItem(position);
        Conversation conv = db.conversationDao().getConversation(user.getIdUser());
        if (conv == null) {
            conv= new Conversation(user.getIdUser());
            db.conversationDao().insert(conv);
            conv= db.conversationDao().getConversation(user.getIdUser());
        }
        Intent intent = new Intent(getActivity(), MessagesActivity.class);
        intent.putExtra("ID_USER", user.getIdUser());
        intent.putExtra("ID_CONVERSATION", conv.getIdConversation());

        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.contact_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);
        if (item.getItemId() == R.id.menu_c_contact_delete) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
             db.userDao().delete(adapter.getItem(info.position));
             adapter.remove(adapter.getItem(info.position));
             setListAdapter(adapter);
             return false;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        users= new ArrayList<>(Arrays.asList(db.userDao().getFriends()));
        adapter = new UserListAdapter(getActivity(), users);
        db.userDao().addObserver(this);
        db.messageDao().addObserver(this);
        setListAdapter(adapter);
        registerForContextMenu(getListView());
    }

    public void filter(CharSequence query) {
        if(adapter != null) {
            adapter.clear();
            adapter.getFilter().filter(query);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof UserClientSide) {
            adapter.add((UserClientSide) o);
        }
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
