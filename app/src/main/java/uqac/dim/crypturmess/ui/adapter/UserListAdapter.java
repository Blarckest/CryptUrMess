package uqac.dim.crypturmess.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.model.entity.User;
import uqac.dim.crypturmess.model.entity.UserClientSide;
import uqac.dim.crypturmess.ui.UserBGManager;

public class UserListAdapter extends BaseAdapter {

    Activity activity;
    UserClientSide[] customListDataModelArrayList;
    LayoutInflater layoutInflater = null;

    public UserListAdapter(Activity activity, UserClientSide[] customListDataModelArray){
        this.activity=activity;
        this.customListDataModelArrayList = customListDataModelArray;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return customListDataModelArrayList.length;
    }

    @Override
    public UserClientSide getItem(int i) {
        return customListDataModelArrayList[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /*private static class ViewHolder{
        ImageView image_view;
        TextView tv_name,tv_discription;

    }
    ViewHolder viewHolder = null;*/

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vi=view;
        String nickname = getItem(i).getNickname();

        if(vi==null) {
            //viewHolder = new ViewHolder();
            vi = layoutInflater.inflate(R.layout.contact_fragment, null);
            ((TextView)vi.findViewById(R.id.c_user_name)).setText(nickname+"("+getItem(i).getUsername()+")");
            ((TextView)vi.findViewById(R.id.c_user_letter_frag)).setText(new String(Character.toChars(nickname.codePointAt(0))).toUpperCase());
            ((ImageView)vi.findViewById(R.id.c_user_icon)).setImageResource(new UserBGManager().getBackgroundByUserName(nickname));
        }

        return vi;
    }
}
