package uqac.dim.crypturmess.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Arrays;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.Conversation;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.ui.activities.MessagesActivity;
import uqac.dim.crypturmess.ui.adapter.UserListAdapter;

public class FragmentContact extends ListFragment {
    private AppLocalDatabase db = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
    private UserListAdapter adapter;
    private ArrayList<UserClientSide> users;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users= new ArrayList<>(Arrays.asList(AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).userDao().getFriends()));
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        UserClientSide user = (UserClientSide) getListAdapter().getItem(position);
        Conversation conv = AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).conversationDao().getConversation(user.getIdUser());
        if (conv == null) {
            conv= new Conversation(user.getIdUser());
            AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).conversationDao().insert(conv);
            conv= AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).conversationDao().getConversation(user.getIdUser());
        }
        Intent intent = new Intent(getActivity(), MessagesActivity.class);
        intent.putExtra("ID_USER", user.getIdUser());
        intent.putExtra("ID_CONVERSATION", conv.getIdConversation());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new UserListAdapter(getActivity(), users);
        setListAdapter(adapter);
    }

    public void filter(CharSequence query) {
        adapter.getFilter().filter(query);
    }
}
