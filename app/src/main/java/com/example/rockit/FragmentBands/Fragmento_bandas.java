package com.example.rockit.FragmentBands;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rockit.Classes.Banda;
import com.example.rockit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragmento_bandas extends Fragment {

    View view;
    //RecycleView Bandas
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Banda> exampleList = new ArrayList<>();
    ArrayList<Float> starsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bands, container, false);
        //Se a RecyclerView estiver dentro do layout do fragment, pegue ele direto da View v inflada
        //SHOW
        //Consultar classe RecycleView_Adapter
        mRecyclerView = view.findViewById(R.id.recycleView); //recycleview dentro do layout fragmento_chats
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecycleView_bandas(getContext(),exampleList,"show");
        mRecyclerView.setAdapter(mAdapter);

            //GET USERS FIREBASE
            readUsers();

        return view;
    }
    public void onResume() {
        super.onResume();
        organizeBandsStars();
        mAdapter.notifyDataSetChanged();
    }

    private void readUsers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bands");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exampleList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Banda bandObject = dataSnapshot.getValue(Banda.class);
                        starsList.add(Float.parseFloat(bandObject.getStars()));
                        exampleList.add(bandObject);

                }
                organizeBandsStars();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void organizeBandsStars(){
        Banda auxiliarBanda;
        Float auxiliarFloat;
        for(int i=0; i<exampleList.size();i++){
            for(int k=i; k<exampleList.size();k++) {
                if(starsList.get(k)>starsList.get(i)) {
                    auxiliarBanda=exampleList.get(i); exampleList.set(i, exampleList.get(k));exampleList.set(k, auxiliarBanda);
                    auxiliarFloat=starsList.get(i); starsList.set(i, starsList.get(k));starsList.set(k, auxiliarFloat);
                }
            }
        }
    }

}
