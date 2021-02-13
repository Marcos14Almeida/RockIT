package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pag1_qual_intrumento_toca extends AppCompatActivity {

    ArrayList<String> ListaInstrumentos = new ArrayList<>();
    ArrayList<String> Lista_MeuInstrumentos = new ArrayList<>();
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_qual_intrumento_toca);

        texto = findViewById(R.id.textView49);
        texto.setText("");
        func_Lista_Instrumentos();
        show_list();


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
    public void show_list(){
        final ListView list = findViewById(R.id.lista);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, ListaInstrumentos);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(Pag1_qual_intrumento_toca.this,clickedItem,Toast.LENGTH_LONG).show();
                Lista_MeuInstrumentos.add(clickedItem);
                ListaInstrumentos.remove(clickedItem);
                //show lista 2
                show_list2();
            }
        });
    }
    public void show_list2(){
        //Titulo da pag: Meus intrumentos praticados
        if(Lista_MeuInstrumentos.size()>0){
            texto.setText(getResources().getString(R.string.instrumentos_praticados));
        }else{texto.setText("");}

        final ListView list2 = findViewById(R.id.lista_2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, Lista_MeuInstrumentos);
        list2.setAdapter(arrayAdapter);
        //Se clicar deleta o item selecionado
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list2.getItemAtPosition(position);
                Lista_MeuInstrumentos.remove(clickedItem);
                ListaInstrumentos.add(clickedItem);
                //show lista 2
                show_list2();
            }
        });
    }
    ////////                FUNÇÕES                      /////////
    public void abrirMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
