package uqac.dim.crypturmess.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uqac.dim.crypturmess.R;
import uqac.dim.crypturmess.model.entity.Message;

public class MessageListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private Message mMessageList[];

    public MessageListAdapter(Context context, Message messageList[]) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = (Message) mMessageList[position];
        if(message.isReceived()) return 1;
        return 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == 1) {
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
        Message message = (Message) mMessageList[position];

        switch (holder.getItemViewType()) {
            case 1:
                ((SentMessageHolder) holder).bind(message);
                break;
            case 2:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.length;
    }


    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        LinearLayout linear;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.mg_message);
            timeText = (TextView) itemView.findViewById(R.id.mg_time);
            linear = (LinearLayout) itemView.findViewById(R.id.mg_linear);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message) {
            messageText.setText(message.getMessage());
            messageText.setBackgroundColor(R.color.gold);
            timeText.setText(DateUtils.formatDateTime(itemView.getContext(), message.getTimestamp(),0));
            linear.setGravity(View.FOCUS_RIGHT);
        }
    }

   private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        LinearLayout linear;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.mg_message);
            timeText = (TextView) itemView.findViewById(R.id.mg_time);
            linear = (LinearLayout) itemView.findViewById(R.id.mg_linear);
        }

        @SuppressLint("ResourceAsColor")
        void bind(Message message) {
            messageText.setText(message.getMessage());
            messageText.setBackgroundColor(R.color.purple_500);
            timeText.setText(DateUtils.formatDateTime(itemView.getContext(), message.getTimestamp(),0));
            linear.setGravity(View.FOCUS_LEFT);
        }
    }
}
