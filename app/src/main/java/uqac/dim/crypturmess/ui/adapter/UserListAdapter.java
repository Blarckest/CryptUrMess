package uqac.dim.crypturmess.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.ui.UserBGManager;

public class UserListAdapter extends ArrayAdapter<UserClientSide> implements Filterable {

    private Activity activity;
    private ArrayList<UserClientSide> users;
    private ArrayList<UserClientSide> allUsers;
    private ArrayList<UserClientSide> res;
    private LayoutInflater layoutInflater = null;
    private CustomFilter filter;
    private AppLocalDatabase db=AppLocalDatabase.getInstance(CrypturMessApplication.getContext());

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    public UserListAdapter(Activity activity, ArrayList<UserClientSide> customListDataModelArray){
        super(activity, R.layout.contact_fragment, customListDataModelArray);
        this.activity=activity;
        this.users = customListDataModelArray;
        this.allUsers = new ArrayList<>(customListDataModelArray);
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filter = new CustomFilter();
        res=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public UserClientSide getItem(int position) {
        return users.get(position);
    }

    @Override
    public int getPosition(@Nullable UserClientSide item) {
        return users.indexOf(item);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi=view;
        String nickname = getItem(i).getNickname();

        if(vi==null) {
            vi = layoutInflater.inflate(R.layout.contact_fragment, null);
            ((TextView)vi.findViewById(R.id.c_user_name)).setText(nickname+"("+getItem(i).getUsername()+")");
            ((TextView)vi.findViewById(R.id.c_user_letter_frag)).setText(new String(Character.toChars(nickname.codePointAt(0))).toUpperCase());
            ((FrameLayout)vi.findViewById(R.id.c_user_bg)).setBackground(new UserBGManager().getBackgroundByUserName(nickname));
            String text=db.messageDao().getLastMessageFromConv(db.conversationDao().getConversation(getItem(i).getIdUser()).getIdConversation());
            ((TextView)vi.findViewById(R.id.c_last_msg)).setText(text==null?"":text);//todo last message
        }
        return vi;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults f = new FilterResults();
            res.clear();
            if (constraint != null) {
                for (int i = 0; i < allUsers.size(); i++) {
                    if (allUsers.get(i).getUsername().toLowerCase().contains(constraint.toString().toLowerCase()) || allUsers.get(i).getNickname().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        res.add(allUsers.get(i));
                    }
                }
                f.values = res;//.toArray();
                f.count = res.size();
            }
            return f;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            users.clear();
            users.addAll((ArrayList<UserClientSide>) results.values);
            notifyDataSetChanged();
        }
    }
}
