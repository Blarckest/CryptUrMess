package uqac.dim.crypturmess.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.TimeUtils;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.model.entity.Message;

public class MessageListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Message> mMessageList;

    public MessageListAdapter(Context context, Message[] messageList) {
        mContext = context;
        mMessageList = new ArrayList<>(Arrays.asList(messageList));
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessageList.get(position);
        if(message.isReceived()) return 2;
        return 1;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Log.i("ADAPT", "ViewType : " + viewType);
        if (viewType ==1) {//getItemViewType(getItemCount())
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bubble_message, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bubble_message, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = mMessageList.get(position);
        Log.i("ADAPT", "holder.getItemViewType() : "+message.isReceived());//holder.getItemViewType()
        if (message.isReceived())
            ((ReceivedMessageHolder) holder).bind(message);
        else
            ((SentMessageHolder) holder).bind(message);


    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public void addMessage(Message msg){
        mMessageList.add(msg);
        notifyDataSetChanged();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        private TextView messageText, timeText;
        private LinearLayout linear;
        private Button openinmaps;
        private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.mg_message);
            timeText = (TextView) itemView.findViewById(R.id.mg_time);
            linear = (LinearLayout) itemView.findViewById(R.id.mg_linear);
            openinmaps = (Button) itemView.findViewById(R.id.buttonOpenInMaps);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message) {
            Pattern p = Pattern.compile("[+-]?[0-9]{2}\\.[0-9]*,[+-]?[0-9]{2}\\.[0-9]*");
            Matcher m;
            m = p.matcher(message.getMessage());

            if (m.find()) {
                Toast.makeText(mContext.getApplicationContext(), "Coordonnes GPS envoyées", Toast.LENGTH_LONG).show();
                openinmaps.setTag(message);
            }
            else {
                openinmaps.setVisibility(View.GONE);
            }

            messageText.setText(message.getMessage());
            messageText.setBackgroundResource(R.drawable.bubble_right);
            timeText.setText(dateFormat.format(new Date(message.getTimestamp())));// DateUtils.formatDateTime(itemView.getContext(), message.getTimestamp(),0)
            linear.setGravity(Gravity.END);
        }
    }

   private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        private TextView messageText, timeText;
        private LinearLayout linear;
       private Button openinmaps;
        private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.mg_message);
            timeText = (TextView) itemView.findViewById(R.id.mg_time);
            linear = (LinearLayout) itemView.findViewById(R.id.mg_linear);
            openinmaps = (Button) itemView.findViewById(R.id.buttonOpenInMaps);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message) {
            Pattern p = Pattern.compile("[+-]?[0-9]{2}\\.[0-9]*,[+-]?[0-9]{2}\\.[0-9]*");
            Matcher m;
            m = p.matcher(message.getMessage());

            if (m.find()) {
                Toast.makeText(mContext.getApplicationContext(), "Coordonnes GPS reçues", Toast.LENGTH_LONG).show();
                openinmaps.setTag(message);
            }
            else {
                openinmaps.setVisibility(View.GONE);
            }

            messageText.setText(message.getMessage());
            messageText.setBackgroundResource(R.drawable.bubble_left);
            messageText.setTextColor(R.color.black);
            timeText.setText(dateFormat.format(new Date(message.getTimestamp())));
            linear.setGravity(Gravity.START);
        }
    }
}
