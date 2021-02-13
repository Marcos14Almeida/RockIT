package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Pag1_bandas_preferidas extends AppCompatActivity {

    ArrayList<String> ListaBandas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_bandas_preferidas);

        func_Lista_Bandas();
        //SHOW LIST
        final ListView list = findViewById(R.id.lista_bandas);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, ListaBandas);
        list.setAdapter(arrayAdapter);
    }

    public void func_Lista_Bandas(){
        ListaBandas.add("Red Hot Chili Peppers");
        ListaBandas.add("Green Day");
        ListaBandas.add("Far From Alaska");
        ListaBandas.add("Bring Me The Horizon");
        ListaBandas.add("MUSE");
        ListaBandas.add("Madonna");
        ListaBandas.add("Bob Marley");
        ListaBandas.add("Beatles");
        ListaBandas.add("Aerosmith");
        ListaBandas.add("Queen");
        ListaBandas.add("Linkin Park");
    }
    ////////                FUNÇÕES                      /////////
    public void abrirPag(View view){
        Intent intent = new Intent(this, Pag1_qual_intrumento_toca.class);
        startActivity(intent);
    }
}
