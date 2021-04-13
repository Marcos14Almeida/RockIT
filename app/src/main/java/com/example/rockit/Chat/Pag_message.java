package com.example.rockit.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockit.Classes.Chat;
import com.example.rockit.Classes.Classe_Geral;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.Notification.APIService;
import com.example.rockit.Notification.Client;
import com.example.rockit.Notification.Data;
import com.example.rockit.Notification.MyResponse;
import com.example.rockit.Notification.Sender;
import com.example.rockit.Notification.Token;
import com.example.rockit.Pag_Profile_Show;
import com.example.rockit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//TUTORIAIS DE APP DE MENSAGEM:
//https://www.youtube.com/watch?v=1mJv4XxWlu8&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=8
//https://www.youtube.com/watch?v=f1HKTg2hyf0&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=7

//NOTIFICAÇÕES
//https://www.youtube.com/watch?v=7Xc_5cduL-Y ->ótimo tutorial, fiz com base nele
//https://www.youtube.com/watch?v=wDpxBTjvPys&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=18

public class Pag_message extends AppCompatActivity {
    Classe_Geral classe_geral = new Classe_Geral();

    String friendUserID,userID;
    String friendName="", userName="";

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    RecycleView_Message messageAdapter_chat;
    List<Chat> mChat;
    ValueEventListener seenListener;

    RecyclerView recyclerView;
    //NOTIFICATION
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_message);

        //Toolbar / barra superior
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //Se clicar no botao de retornar
                toolbar.setNavigationOnClickListener(v -> finish());


        //Recycleview do texto do chat
        recyclerView=findViewById(R.id.recycleView);//recycleview dentro do layout activity_pag_messages
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        //Pega o ID do usuario do amigo da conversa
        Intent intent = getIntent(); //vem do arquivo Recycler View Adapter
        friendUserID = intent.getStringExtra("userID");

        //Notification
        //https://www.youtube.com/watch?v=7Xc_5cduL-Y 8:42
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        //INICIA FIREBASE
        //PEGA O NOME DOS USUARIOS E LE AS MENSAGENS DA CONVERSA
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                assert user != null;
                userName = user.getName();
                userID = user.getId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //seleciona o usuario do amigo da conversa
        reference = FirebaseDatabase.getInstance().getReference("Users").child(friendUserID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);

                //Define o nome do usuario no topo da pagina
                TextView username=findViewById(R.id.textName);
                assert user != null;
                username.setText(user.getName());

                friendName = user.getName();

                //Atualiza mensagens da conversa
                readMessage(firebaseUser.getUid(), friendUserID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateToken();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    private void updateToken(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        assert firebaseUser != null;
        FirebaseDatabase.getInstance().getReference("Tokens").child(firebaseUser.getUid()).setValue(token);
    }

    //////////////////////////////////////////////////////
    //                     MENU ITEMS                   //
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.message_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //BLOQUEAR USUARIO
        if (item.getItemId() == R.id.item1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Deseja realmente bloquear o usuário? (A operação é irreversível)");
            builder.setPositiveButton("OK", (dialog, which) -> {
                Toast.makeText(getApplicationContext(), "Usuário bloqueado", Toast.LENGTH_SHORT).show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.child(friendUserID).child("connections").child("no").child(firebaseUser.getUid()).setValue(true);
                reference.child(friendUserID).child("connections").child("yes").child(firebaseUser.getUid()).removeValue();
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    //ATUALIZA MENSAGENS NO LAYOUT DA PAGINA
    private void readMessage(final String myid, final String userid){
        mChat = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    assert chat != null;
                    //Seleciona todas as conversas do chat que contenham os dois usuarios
                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){

                        mChat.add(chat);

                        //Se a mensagem for de outra pessoa, e enviada por mim
                        if(chat.getSender().equals(userid) && chat.getReceiver().equals(myid)){
                        //DOUBLE CHECK IF user has seen message https://www.youtube.com/watch?v=E1qm09JWUdI
                        HashMap <String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen",true);
                        dataSnapshot.getRef().updateChildren(hashMap);}
                    }




                    //SHOW no Recycle Adapter do arquivo MessageAdapter_Chat
                    messageAdapter_chat = new RecycleView_Message(Pag_message.this,mChat);
                    recyclerView.setAdapter(messageAdapter_chat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    //////////////////////////////////////////////////////
    //     B O T A O    S E N D     M E S S A G E       //
    public void send(View view){
        EditText write_message = findViewById(R.id.write_message);
        String msg = write_message.getText().toString();
        write_message.setText("");

        if(!msg.equals("")){
            sendMessage(firebaseUser.getUid(),friendUserID,msg);
        }else{
            Toast.makeText(this,"Mensagem Vazia!!!", Toast.LENGTH_SHORT).show();
        }
    }

    //////////////////////////////////////////////////////
    //            SEND/RECEIVE   MESSAGES               //

        private void sendMessage(String sender, String receiver, String message){

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender",sender); //Salva o ID de quem enviou
            hashMap.put("receiver",receiver);//Salva o ID de quem recebeu
            hashMap.put("message",message);
            hashMap.put("isseen",false);
            //HORARIO MENSAGEM
            DateFormat df = new SimpleDateFormat("HH:mm;dd/MM/yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            hashMap.put("timestamp",date);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Chats").push().setValue(hashMap);

            //NOTIFICATION
            //////////////////////////////// *** IMPORTANTE *** ////////////////////////////////////
            //Esse receiver determina pra quem vai mensagem / no caso pega o token de quem vai receber a mensagem
            reference = FirebaseDatabase.getInstance().getReference("Tokens");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //receiver = ID do amigo que vai receber a msg
                        sendNotification(snapshot.child(receiver).child("token").getValue().toString(),userName, message);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    //////////////////////////////////////////////////////
    //                  NOTIFICATION                    //
    //https://www.youtube.com/watch?v=wDpxBTjvPys&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=18
    private void sendNotification(String friendToken, String username, String message){
                    Log.d("Pag_message.java","SendNotification: \nFriend Token: "+friendToken+"\nUsername: "+username+"\n"+message);

                    //sented, user, title, msg
                    Data data = new Data(userID,friendToken,username,message);
                    Sender sender = new Sender(data, friendToken);
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse( Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code()==200){
                                assert response.body() != null;
                                if(response.body().success != 1){
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();}
                                else{
                                    Toast.makeText(getApplicationContext(), "SendNotification: \nFriend Token:" +friendToken+"\nUsername: "+username+"\n"+message, Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure( Call<MyResponse> call,  Throwable t) {}
                    });
    }


    //////////////////////////////////////////////////////

    public void ir_pag_amigo(View view){
        Intent intent = new Intent(this, Pag_Profile_Show.class);
        intent.putExtra("userID",friendUserID);
        startActivity(intent);
    }
    ////////                STATUS - Is User Online?                     /////////
    @Override
    protected void onResume(){
        super.onResume();
        classe_geral.updateFieldUsers("status","online");
    }
    @Override
    protected void onPause(){
        super.onPause();

        classe_geral.updateFieldUsers("status","offline");

        //HORARIO ULTIMA VEZ ONLINE
        DateFormat df = new SimpleDateFormat("HH:mm;dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        classe_geral.updateFieldUsers("last_seen",date);
    }


}
