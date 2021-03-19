package com.example.rockit.Chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rockit.Classes.Chat;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecycleView_Message extends RecyclerView.Adapter<RecycleView_Message.ViewHolder>{
    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;

    private Context mcontext;
    private List<Chat> mChat;

    RecycleView_Message(Context context, List<Chat> exampleList, String imageurl) {
        this.mChat = exampleList;
        this.mcontext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder result;
        if (viewType == MSG_TYPE_RIGHT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            result = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            result = new ViewHolder(v);
        }
        return result;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//Holder contem a imagem e texto das mensagens
        Chat chat = mChat.get(position);//pega os dados do chat

        //TUDO ISSO PARA MOSTRAR
        //FOTO DO AMIGO DA CONVERSA

        //pega o ID do usuario que mandou a msg para mostrar a foto dele
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(chat.getSender());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);//pega os dados da classe usuario de quem enviou a mensagem
                if (user != null) {
                    holder.show_message.setText(chat.getMessage());
                    holder.show_message2.setText(chat.getTimestamp());
                    if(user.getImageURL().equals("default")){holder.profile_picture.setImageResource(R.drawable.foto_pessoa);}
                    else{
                        // Avalia se o mcontext está certo, pq se não tem um bug
                        //https://stackoverflow.com/questions/39093730/you-cannot-start-a-load-for-a-destroyed-activity-in-relativelayout-image-using-g
                        if (isValidContextForGlide(mcontext)) {
                            //Define a imagem
                            Glide.with(mcontext).load(user.getImageURL()).into(holder.profile_picture);
                            }
                        }
                }

            }            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });


    }
    private static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }
    public int getItemCount(){
        return mChat.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_picture;
        TextView show_message,show_message2;
        ViewHolder(View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.im_friend_user);
            show_message = itemView.findViewById(R.id.texto_message);
            show_message2 = itemView.findViewById(R.id.texto_message2);
        }
    }

    public int getItemViewType(int position){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        if(mChat.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
