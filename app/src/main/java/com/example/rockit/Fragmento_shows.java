package com.example.rockit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Fragmento_shows extends Fragment {
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
        View view = inflater.inflate(R.layout.fragmento_shows, container, false);


        // Configure the RecyclerView
        //https://www.youtube.com/watch?v=17NbUcEts9c
        //https://codinginflow.com/tutorials/android/simple-recyclerview-java/part-2-adapter
        ArrayList<Usuario> exampleList = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        nomes.add("Alice");
        nomes.add("Alice2");
        ArrayList<String> local = new ArrayList<>();
        local.add("São Paulo");
        local.add("Campinas");
        ArrayList<String> description = new ArrayList<>();
        description.add("Top do Mundo é sensação");
        description.add("Da-lhe komo se fosse ontem");

        for(int i=0; i<nomes.size();i++){
            exampleList.add(new Usuario( nomes.get(i), local.get(i),description.get(i),R.drawable.foto_banda_generica));
        }
        exampleList.add(new Usuario( "LOUCURA TOTAL", "Zona Oeste","To fazendo um cover me dem seus dim dim",R.drawable.foto_usuario));
        exampleList.add(new Usuario( "Alice In Chains", "Zona Sul","Top do Mundo é sensação",R.drawable.foto_banda_generica));
        exampleList.add(new Usuario( "Sum 41", "Mogi Mirim","ist.add(new Usuario( Alice In Ch",R.drawable.foto_capa_album));
        exampleList.add(new Usuario( "Chimbinha", "Zona Oeste","To fazendo um cover me dem seus dim dim",R.drawable.foto_pessoa));
        exampleList.add(new Usuario( "Alice In Chains", "Zona Sul","Top do Mundo é sensação",R.drawable.foto_banda_generica));
        exampleList.add(new Usuario( "Sum 41", "Mogi Mirim","ist.add(new Usuario( Alice In Ch",R.drawable.foto_capa_album));
        exampleList.add(new Usuario( "Chimbinha", "Zona Oeste","To fazendo um cover me dem seus dim dim",R.drawable.foto_pessoa));
        exampleList.add(new Usuario( "Alice In Chains", "Zona Sul","Top do Mundo é sensação",R.drawable.foto_banda_generica));
        exampleList.add(new Usuario( "Sum 41", "Mogi Mirim","ist.add(new Usuario( Alice In Ch",R.drawable.foto_usuario));
        exampleList.add(new Usuario( "Chimbinha", "Zona Oeste","To fazendo um cover me dem seus dim dim",R.drawable.foto_pessoa));
        exampleList.add(new Usuario( "Alice In Chains", "Zona Sul","Top do Mundo é sensação",R.drawable.foto_usuario));
        exampleList.add(new Usuario( "Sum 41", "Mogi Mirim","ist.add(new Usuario( Alice In Ch",R.drawable.foto_capa_album));
        exampleList.add(new Usuario( "Chimbinha", "Zona Oeste","To fazendo um cover me dem seus dim dim",R.drawable.foto_pessoa));
        exampleList.add(new Usuario( "Alice In Chains", "Zona Sul","Top do Mundo é sensação",R.drawable.foto_banda_generica));
        exampleList.add(new Usuario( "Sum 41", "Mogi Mirim","ist.add(new Usuario( Alice In Ch",R.drawable.foto_capa_album));
        exampleList.add(new Usuario( "Chimbinha", "Zona Oeste","To fazendo um cover me dem seus dim dim",R.drawable.foto_usuario));
        exampleList.add(new Usuario( "Alice In Chains", "Zona Sul","Top do Mundo é sensação",R.drawable.foto_banda_generica));
        exampleList.add(new Usuario( "Sum 41", "Mogi Mirim","ist.add(new Usuario( Alice In Ch",R.drawable.foto_capa_album));
        exampleList.add(new Usuario( "Chimbinha", "Zona Oeste","To fazendo um cover me dem seus dim dim",R.drawable.foto_pessoa));
        exampleList.add(new Usuario( "Alice In Chains", "Zona Sul","Top do Mundo é sensação",R.drawable.foto_banda_generica));
        exampleList.add(new Usuario( "Sum 41", "Mogi Mirim","ist.add(new Usuario( Alice In Ch",R.drawable.foto_usuario));
        exampleList.add(new Usuario( "Chimbinha", "Zona Oeste","To fazendo um cover me dem seus dim dim",R.drawable.foto_pessoa));

        mRecyclerView = view.findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecycleView_Adapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

}
