package uqac.dim.crypturmess.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.ui.activities.MessagesActivity;
import uqac.dim.crypturmess.ui.adapter.UserListAdapter;

public class FragmentContact extends ListFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AppLocalDatabase db = AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
        //db.userDao().insert(new UserClientSide("ondqndzoindnqodfzejbfehhzeofozan", "ok boomer", "mon chum"));
        UserListAdapter adapter = new UserListAdapter(getActivity(), AppLocalDatabase.getInstance(CrypturMessApplication.getContext()).userDao().getFriends());
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        UserClientSide user = (UserClientSide) getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), MessagesActivity.class);
        intent.putExtra("ID_USER", user.getIdUser());
        intent.putExtra("ID_CONVERSATION", id);
        startActivity(intent);
    }
}
