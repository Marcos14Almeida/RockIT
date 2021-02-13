package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Pag1_qual_intrumento_toca extends AppCompatActivity {

    ArrayList<String> ListaInstrumentos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_qual_intrumento_toca);

        func_Lista_Instrumentos();
        //SHOW LIST
        final ListView list = findViewById(R.id.lista);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, ListaInstrumentos);
        list.setAdapter(arrayAdapter);

    }
    public void func_Lista_Instrumentos(){
        ListaInstrumentos.add("Banjo");
        ListaInstrumentos.add("Bateria");
        ListaInstrumentos.add("Baixo");
        ListaInstrumentos.add("Cantor");
        ListaInstrumentos.add("Compositor");
        ListaInstrumentos.add("Dançarino");
        ListaInstrumentos.add("DJ");
        ListaInstrumentos.add("Produtor Musical");
        ListaInstrumentos.add("Castanholas");
        ListaInstrumentos.add("Chocalho");
        ListaInstrumentos.add("Clarinete");
        ListaInstrumentos.add("Cavaquinho");
        ListaInstrumentos.add("Contrabaixo");
        ListaInstrumentos.add("Guitarra Elétrica");
        ListaInstrumentos.add("Guitarra Havaiana");
        ListaInstrumentos.add("Guitarra Portuguesa");
        ListaInstrumentos.add("Harpa");
        ListaInstrumentos.add("Oboé");
        ListaInstrumentos.add("Órgão");
        ListaInstrumentos.add("Pandeiro");
        ListaInstrumentos.add("Piano");
        ListaInstrumentos.add("Reco-Reco");
        ListaInstrumentos.add("Sanfona/Acordeão");
        ListaInstrumentos.add("Saxofone");
        ListaInstrumentos.add("Sintetizador");
        ListaInstrumentos.add("Surdo");
        ListaInstrumentos.add("Triângulo");
        ListaInstrumentos.add("Tuba");
        ListaInstrumentos.add("Trombone");
        ListaInstrumentos.add("Trompa");
        ListaInstrumentos.add("Trompete");
        ListaInstrumentos.add("Ukelele");
        ListaInstrumentos.add("Viola");
        ListaInstrumentos.add("Violino");
        ListaInstrumentos.add("Violão");
        ListaInstrumentos.add("Violoncelo");
        ListaInstrumentos.add("Xilofone");
    }
    ////////                FUNÇÕES                      /////////
    public void abrirMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
