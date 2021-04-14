package com.example.rockit.Chat;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rockit.Cadastro.Pag0_login;
import com.example.rockit.Classes.Chat;
import com.example.rockit.Classes.Classe_Geral;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    RecycleView_Message(Context context, List<Chat> exampleList) {
        this.mChat = exampleList;
        this.mcontext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {//Holder contem a imagem e texto das mensagens
        Chat chat = mChat.get(position);//pega os dados do chat

        //TUDO ISSO PARA MOSTRAR
        //FOTO DO AMIGO DA CONVERSA

        //pega o ID do usuario que mandou a msg para mostrar a foto dele
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(chat.getSender());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);//pega os dados da classe usuario de quem enviou a mensagem

                    //Message
                    holder.show_message.setText(chat.getMessage());

                    //Pega a data do firebase e converte para o formato mais legivel
                    Classe_Geral classe_geral = new Classe_Geral();
                    holder.show_message2.setText(classe_geral.Data(chat.getTimestamp()));

                    //IMAGEM
                    if(user.getImageURL().equals("default")){holder.profile_picture.setImageResource(R.drawable.foto_pessoa);}
                    else{
                        // Avalia se o mcontext está certo, pq se não tem um bug
                        //https://stackoverflow.com/questions/39093730/you-cannot-start-a-load-for-a-destroyed-activity-in-relativelayout-image-using-g
                        if (isValidContextForGlide(mcontext)) {
                            //Define a imagem
                            Glide.with(mcontext).load(user.getImageURL()).into(holder.profile_picture);
                            }
                        }

                    //Is seen - o usuario visualizou a mensagem
                    //https://www.youtube.com/watch?v=E1qm09JWUdI 5:25
                if(position == mChat.size()-1){
                    holder.show_message.setText(chat.getMessage()+"     ");//para ter espaço para o double check
                    if(chat.getIsseen()){
                        holder.im_seen.setImageResource(R.drawable.doublecheck);
                    }else{
                        holder.im_seen.setImageResource(R.drawable.doublecheck_gray);
                    }
                }else{
                    holder.im_seen.setVisibility(View.GONE);
                }


                //ABRE DIALOG MESSAGE PARA:
                // COPIAR OU DELETAR TEXTO
                holder.show_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogMessage(chat);
                    }
                });


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
        ImageView profile_picture,im_seen;
        TextView show_message,show_message2;
        ViewHolder(View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.im_friend_user);
            show_message = itemView.findViewById(R.id.texto_message);
            show_message2 = itemView.findViewById(R.id.texto_message2);
            im_seen = itemView.findViewById(R.id.im_seen);
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

    //////////////////////////////////////////////////////////////////////////////////////////////
    //     copiar ou deletar texto
    public void dialogMessage(Chat chat){
        AlertDialog.Builder dialog = new AlertDialog.Builder(mcontext);
        dialog.setCancelable(true);

        //                    COPY TEXT                     //
        dialog.setPositiveButton("Copiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //https://stackoverflow.com/questions/19177231/android-copy-paste-from-clipboard-manager
                ClipboardManager clipboard = (ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copy",chat.getMessage());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mcontext,"Texto Copiado",Toast.LENGTH_SHORT).show();
            }
        });

        //                    DELETE TEXT                     //
        dialog.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference referenceTemp = FirebaseDatabase.getInstance().getReference("Chats");
                referenceTemp.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("message").getValue().equals(chat.getMessage())){
                                referenceTemp.child(dataSnapshot.getKey()).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });

        //show dialog
        AlertDialog alerta = dialog.create();
        alerta.show();
    }
}
