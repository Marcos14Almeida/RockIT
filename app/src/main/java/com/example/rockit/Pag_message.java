package com.example.rockit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.rockit.Classes.Chat;
import com.example.rockit.Classes.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pag_message extends AppCompatActivity {

    TextView username;
    TextView write_message;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    MessageAdapter_Chat messageAdapter_chat;
    List<Chat> mChat;

    RecyclerView recyclerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_message);

        username=findViewById(R.id.textName);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Recycleview do texto do chat
        recyclerView=findViewById(R.id.recycleView);//recycleview dentro do layout activity_pag_messages
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        //Pega o ID da foto selecionada
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        username.setText(userID);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users"); //acrescentar getID quando funcionar;


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Usuario user = snapshot.getValue(Usuario.class);
                //username.setText(user.getName());
                //if(user.getImageURL().equals("default")){}
                //readMessage(firebaseUser.getUid(), userID, user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        private void sendMessage(String sender, String receiver, String message){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender",sender);
            hashMap.put("receiver",receiver);
            hashMap.put("message",message);

            reference.child("Chats").push().setValue(hashMap);
        }
    private void readMessage(final String myid, final String userid,final  String imageurl){
        mChat = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    assert chat != null;
                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                        chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mChat.add(chat);
                    }
                    messageAdapter_chat = new MessageAdapter_Chat(Pag_message.this,mChat,imageurl);
                    recyclerView.setAdapter(messageAdapter_chat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //////////////////////////////////////////////////////
    //            B O T A O    S E N D                  //
    public void send(View view){
        //sendMessage("oi","eae","suave");
        write_message=findViewById(R.id.write_message);
        write_message.setText("");
        Toast.makeText(this,"Mensagem Enviada", Toast.LENGTH_SHORT).show();
    }

}
