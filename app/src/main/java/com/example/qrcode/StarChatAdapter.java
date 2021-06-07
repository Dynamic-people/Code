package com.example.qrcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StarChatAdapter extends RecyclerView.Adapter<StarChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNickName;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_nickname;
        public TextView TextView_msg;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_nickname = v.findViewById(R.id.TextView_nickname);
            TextView_msg = v.findViewById(R.id.TextView_msg);
            rootView = v;
        }


    }


    public StarChatAdapter(List<ChatData> myDataset, Context context) {

        mDataset = myDataset;
        this.myNickName = myNickName;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.star_row_chat, parent, false);

        MyViewHolder holder = new MyViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatData chat = mDataset.get(position);

        holder.TextView_nickname.setText(chat.getName());
        holder.TextView_msg.setText(chat.getMsg());
        holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

        /*if(chat.getName().equals(this.myNickName)) {
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        else {
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }*/
    }



    @Override
    public int getItemCount() {
        return mDataset == null ? 0 :  mDataset.size();
    }
    public ChatData getChat(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatData chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1); //갱신
    }
}
