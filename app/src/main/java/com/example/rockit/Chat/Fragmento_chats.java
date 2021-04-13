package com.example.rockit.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rockit.Classes.Usuario;
import com.example.rockit.Notification.Token;
import com.example.rockit.R;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
// Configure the RecyclerView
//https://www.youtube.com/watch?v=17NbUcEts9c
//https://codinginflow.com/tutorials/android/simple-recyclerview-java/part-2-adapter

public class Fragmento_chats extends Fragment {
    private FirebaseUser firebaseUser;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_chats, container, false);

        //Consultar classe RecycleView_Adapter
        mRecyclerView = view.findViewById(R.id.recycleView); //recycleview dentro do layout fragmento_chats
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());

        //GET USERS FIREBASE
        readUsers();

        return view;
    }

    private void readUsers(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        ArrayList<Usuario> exampleList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exampleList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Usuario oneUser = dataSnapshot.getValue(Usuario.class);
                    //garante que tem algum valor disponível
                    assert oneUser != null;
                    assert firebaseUser != null;
                    //Se não for o meu usuario mostra na lista de chats
                    if(dataSnapshot.child("connections").child("yes").hasChild(firebaseUser.getUid()) && //se o outro usuario tem meu nome
                            snapshot.child(firebaseUser.getUid()).child("connections").child("yes").hasChild(oneUser.getId()) && //se eu tenho o nome do outro usuario
                            oneUser.getName().equals(dataSnapshot.child("name").getValue())   ){
                        exampleList.add(oneUser);
                    }

                    //SHOW
                    mAdapter = new RecycleView_Chat(getContext(),exampleList);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }
                //FAZ PARTE DA NOTIFICACAO
                updateToken(FirebaseInstanceId.getInstance().getToken());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //////////////////////////////////////////////////////
    //                  NOTIFICATION                    //
    //https://www.youtube.com/watch?v=wDpxBTjvPys&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=18
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);
    }
}
