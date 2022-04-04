package uqac.dim.crypturmess.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import uqac.dim.crypturmess.R;

public class FragmentContact extends ListFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), , android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);*/
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {

    }
}
