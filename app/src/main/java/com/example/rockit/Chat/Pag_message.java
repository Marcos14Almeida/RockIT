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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//TUTORIAIS DE APP DE MENSAGEM:
//https://www.youtube.com/watch?v=1mJv4XxWlu8&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=8
//https://www.youtube.com/watch?v=f1HKTg2hyf0&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=7

public class Pag_message extends AppCompatActivity {

    String userID;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    RecycleView_Message messageAdapter_chat;
    List<Chat> mChat;

    RecyclerView recyclerView;
    APIService apiService;
    Boolean notify=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_message);

        //Toolbar / barra superior
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //Se clicar no botao de retornar
                toolbar.setNavigationOnClickListener(v -> finish());
        //Notification
        apiService = Client.getClient("https://fcm.googleapi.com/").create(APIService.class);


        //Recycleview do texto do chat
        recyclerView=findViewById(R.id.recycleView);//recycleview dentro do layout activity_pag_messages
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        //Pega o ID do usuario do amigo da conversa
        Intent intent = getIntent(); //vem do arquivo Recycler View Adapter
        userID = intent.getStringExtra("userID");

        //INICIA FIREBASE
        //seleciona o usuario do amigo da conversa
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);

                //Define o nome do usuario no topo da pagina
                TextView username=findViewById(R.id.textName);
                assert user != null;
                username.setText(user.getName());

                //Manda mensagem
                readMessage(firebaseUser.getUid(), userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                reference.child(userID).child("connections").child("no").child(firebaseUser.getUid()).setValue(true);
                reference.child(userID).child("connections").child("yes").child(firebaseUser.getUid()).removeValue();
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


    //////////////////////////////////////////////////////
    //            SEND/RECEIVE   MESSAGES               //

        private void sendMessage(String sender, String receiver, String message){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender",sender); //Salva o ID de quem enviou
            hashMap.put("receiver",receiver);//Salva o ID de quem recebeu
            hashMap.put("message",message);
            //HORARIO MENSAGEM
            DateFormat df = new SimpleDateFormat("HH:mm;dd/MM/yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            hashMap.put("timestamp",date);

            reference.child("Chats").push().setValue(hashMap);

            //NOTIFICATION
            final String msg = message;
            reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Usuario user = snapshot.getValue(Usuario.class);
                    if(notify) {
                        assert user != null;
                        sendNotification(receiver, user.getName(), msg);
                    }
                    notify=false;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
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
    //                  NOTIFICATION                    //
    //https://www.youtube.com/watch?v=wDpxBTjvPys&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=18
    private void sendNotification(String receiver, String username, String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        Log.d("TESTE","ENVIO2"+firebaseUser.getUid()+"\n"+username+"\n"+receiver+"\n"+tokens);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren() ){
                    Token token = dataSnapshot.getValue(Token.class);
                    Data data = new Data(firebaseUser.getUid(),R.mipmap.ic_launcher,username+": "+message,"New Message",userID);
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code()==200){
                                if(response.body().success == 1){
                                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {}
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    //////////////////////////////////////////////////////
    //     B O T A O    S E N D     M E S S A G E       //
    public void send(View view){
        notify=true;
        EditText write_message = findViewById(R.id.write_message);
        String msg = write_message.getText().toString();
        if(!msg.equals("")){
            sendMessage(firebaseUser.getUid(),userID,msg);
        }else{
            Toast.makeText(this,"Mensagem Vazia!!!", Toast.LENGTH_SHORT).show();
        }
        write_message=findViewById(R.id.write_message);
        write_message.setText("");
    }

    //////////////////////////////////////////////////////

    public void ir_pag_amigo(View view){
        Intent intent = new Intent(this, Pag_Profile_Show.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
    }

}
