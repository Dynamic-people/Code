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

public class TwoAdapter extends RecyclerView.Adapter<TwoAdapter.MyViewHolder>{
    private List<ChatDataT> tDataset;
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


    public TwoAdapter(List<ChatDataT> myDataset, Context context) {

        tDataset = myDataset;
        this.myNickName = myNickName;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    @NonNull
    @Override
    public TwoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.two_row_chat, parent, false);

        TwoAdapter.MyViewHolder holder = new TwoAdapter.MyViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull TwoAdapter.MyViewHolder holder, int position) {
        ChatDataT chat = tDataset.get(position);

        holder.TextView_nickname.setText(chat.getName());
        holder.TextView_msg.setText(chat.getMsg());
        holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

    }



    @Override
    public int getItemCount() {
        return tDataset == null ? 0 :  tDataset.size();
    }
    public ChatDataT getChat(int position) {
        return tDataset != null ? tDataset.get(position) : null;
    }

    public void addChat(ChatDataT chat) {
        tDataset.add(chat);
        notifyItemInserted(tDataset.size()-1); //갱신
    }
}
