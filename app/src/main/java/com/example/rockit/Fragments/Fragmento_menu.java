package com.example.rockit.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rockit.Extra_Barra_inferior;
import com.example.rockit.Pag_banda;
import com.example.rockit.Pag_perfil_usuario;
import com.example.rockit.R;

public class Fragmento_menu extends Fragment {

    public Fragmento_menu() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragmento_menu newInstance(String param1, String param2) {
        Fragmento_menu fragment = new Fragmento_menu();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_menu, container, false);

        return view;
    }



    public void abrirPag_banda(View view){
        Intent intent = new Intent(getActivity(), Pag_banda.class);
        startActivity(intent);
    }
    public void abrir_Bottom(View view){
        Intent intent = new Intent(getActivity(), Extra_Barra_inferior.class);
        startActivity(intent);
    }
    public void abrirPag_perfil_usuario(View view){
        Intent intent = new Intent(getActivity(), Pag_perfil_usuario.class);
        startActivity(intent);
    }
}
