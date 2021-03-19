package com.example.rockit.Chat;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecycleView_Chat extends RecyclerView.Adapter<RecycleView_Chat.ExampleViewHolder> {
    private ArrayList<Usuario> mExampleList;
    private Context mcontext;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ImageView imageStatus;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.im_banda);
            imageStatus = itemView.findViewById(R.id.im_status);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);
            mTextView4 = itemView.findViewById(R.id.textView400);
        }
    }
    public RecycleView_Chat(Context context, ArrayList<Usuario> exampleList) {
        this.mExampleList = exampleList;
        this.mcontext = context;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_chat, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Usuario user = mExampleList.get(position);
        //IMAGES FROM CHAT USERS
        if(user.getImageURL().equals("default")){
            holder.mImageView.setImageResource(R.drawable.foto_pessoa);
        }else{
            Glide.with(mcontext).load(user.getImageURL()).into(holder.mImageView);
        }
        //STATUS FROM CHAT USERS
        if(user.getStatus().equals("offline")){
            holder.imageStatus.setVisibility(View.VISIBLE);
            holder.imageStatus.setImageResource(R.drawable.circulo_vermelho);
        }else{
            holder.imageStatus.setVisibility(View.VISIBLE);
            holder.imageStatus.setImageResource(R.drawable.circulo_verde);
        }

        holder.mTextView1.setText(user.getName());
        holder.mTextView2.setText(cidade_user(user));
        holder.mTextView3.setText("Toca: "+user.getInstrumentos());

        //LAST MESSAGE
        lastMessage(user.getId(), holder.mTextView4);

        //MATCH
        matchValidation(user, holder.mTextView2);

        //SE CLICA NA IMAGEM ABRE A PAG DE MENSAGENS
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, Pag_message.class);
                intent.putExtra("userID",user.getId());
                mcontext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public String cidade_user(Usuario user){
        //Display Latitude para teste

        Geocoder geocoder = new Geocoder(mcontext, Locale.getDefault());
        double lat = Double.parseDouble(user.getLatitude());
        double lon = Double.parseDouble(user.getLongitude());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat,lon, 1);
            String yourCity = addresses.get(0).getAddressLine(0);
            if(yourCity.contains("-")) {
                String subString = yourCity.split("-")[1];//Rua Saude,56 - Vila Aurora, São Paulo - SP, 123523-23
                return subString.split(",")[1];//Vila Aurora, São Paulo
            }else return yourCity;
        }
        catch(IOException e) {e.printStackTrace();}
        return "";
    }

    public void matchValidation(Usuario user, TextView textView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if(dataSnapshot.child("connections").child("yes").hasChild(firebaseUser.getUid()) &&
                    user.getName().equals(dataSnapshot.child("name").getValue())   ){

                        fireBaseDataUser(user,textView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    public void fireBaseDataUser(Usuario user, TextView textView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child("connections").child("yes").hasChild(user.getId())){
                textView.setText("MATCH ;) !!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    //https://www.youtube.com/watch?v=t4yTj0yfoMs&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=17
    String theLastMessage;
    public void lastMessage(final String userID, TextView last_msg){
        theLastMessage = "default";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userID) ||
                            chat.getReceiver().equals(userID) && chat.getSender().equals(firebaseUser.getUid())) {
                        theLastMessage = chat.getMessage();
                    }
                }
                switch (theLastMessage){
                    case "default":
                        last_msg.setText("No Message");
                        break;
                    default:
                        last_msg.setText("Last Msg: "+theLastMessage);
                        break;
                }
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


}
