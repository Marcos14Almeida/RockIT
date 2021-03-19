package com.example.rockit.Bandas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rockit.Classes.Banda;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.MainActivity;
import com.example.rockit.R;
import com.example.rockit.FragmentBands.RecycleView_bandas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Pag_List_Your_Band extends AppCompatActivity {
    //RecycleView Bandas
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Banda> exampleList = new ArrayList<>();
    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_list_your_band);

        //Consultar classe RecycleView_Adapter
        mRecyclerView = findViewById(R.id.recycleView); //recycleview dentro do layout fragmento_chats
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        minhasBandas();

        //Toolbar / barra superior
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Se clicar no botao de retornar
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

    }

    public void onPause(){
        super.onPause();
        exampleList.clear();
    }

    /////////////////////////////////////////////////////////////
    public void addBand(View view){
        Intent intent = new Intent(this, Pag_Create_Band.class);
        startActivity(intent);
        finish();
    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void minhasBandas(){
        //INICIA FIREBASE
        //Procura se meu nome tem alguma banda associada
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bands");

        fireBaseDataUser();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Banda band = snapshot.getValue(Banda.class);
                    if(band.getMembers().contains(user.getName())){
                            exampleList.add(band);
                    }

                    //SHOW
                    mAdapter = new RecycleView_bandas(getApplicationContext(), exampleList,"edit");
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //PEGA DADOS DO USUARIO
    public void fireBaseDataUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(Usuario.class);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
