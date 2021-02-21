package com.example.rockit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rockit.Classes.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter_Chat extends RecyclerView.Adapter<MessageAdapter_Chat.ViewHolder>{
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mcontext;
    private List<Chat> mChat;
    private String imageURL;
    FirebaseUser firebaseUser;

    public MessageAdapter_Chat(Context context, List<Chat> exampleList,String imageURL) {
        this.mChat = exampleList;
        this.mcontext = context;
        this.imageURL = imageURL;
    }
    @Override
    public MessageAdapter_Chat.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
        return new MessageAdapter_Chat.ViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter_Chat.ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(MessageAdapter_Chat.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        if(holder.equals("default")){
            holder.profile_picture.setImageResource(R.drawable.foto_banda_generica);
        }
        else{
            holder.profile_picture.setImageResource(R.drawable.foto_usuario);
        }

    }
    public int getItemCount(){
        return mChat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profile_picture;
        public TextView show_message;
        public ViewHolder(View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.imageView);
            show_message = itemView.findViewById(R.id.textView);
        }
    }

    public int getItemViewType(int position){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
