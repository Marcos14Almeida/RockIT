package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pag_0login_teste_musical extends AppCompatActivity {

    ArrayList<String> ListaInstrumentos = new ArrayList<>();
    ArrayList<String> ListaEstilosMusicais = new ArrayList<>();
    ArrayList<String> Lista_MeuInstrumentos = new ArrayList<>();
    ArrayList<String> Lista_MeuEstilosMusicais = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_0login_teste_musical);


        func_Lista_Instrumentos();
        func_Lista_Estilos_Musicais();

        final ListView list = findViewById(R.id.lista_1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaInstrumentos);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(Pag_0login_teste_musical.this,clickedItem,Toast.LENGTH_LONG).show();
                Lista_MeuInstrumentos.add(clickedItem);
                //show lista 2
                show_list2();

            }
        });
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
    public void func_Lista_Estilos_Musicais() {
        ListaEstilosMusicais.add("Blues");
        ListaEstilosMusicais.add("Clássica");
        ListaEstilosMusicais.add("DeathCore");
        ListaEstilosMusicais.add("Death Metal");
        ListaEstilosMusicais.add("Eletrônica");
        ListaEstilosMusicais.add("Emo");
        ListaEstilosMusicais.add("Funky");
        ListaEstilosMusicais.add("Funky Rock");
        ListaEstilosMusicais.add("Gótico");
        ListaEstilosMusicais.add("Groove");
        ListaEstilosMusicais.add("Grunge");
        ListaEstilosMusicais.add("Hardcore");
        ListaEstilosMusicais.add("Heavy Metal");
        ListaEstilosMusicais.add("Hard Rock");
        ListaEstilosMusicais.add("Hip-Hop");
        ListaEstilosMusicais.add("Indie Rock");
        ListaEstilosMusicais.add("Jazz");
        ListaEstilosMusicais.add("MetalCore");
        ListaEstilosMusicais.add("Metal Alternativo");
        ListaEstilosMusicais.add("Metal Industrial");
        ListaEstilosMusicais.add("Metal Progressivo");
        ListaEstilosMusicais.add("Metal Sinfônico");
        ListaEstilosMusicais.add("Nu Metal");
        ListaEstilosMusicais.add("Ópera");
        ListaEstilosMusicais.add("Pop");
        ListaEstilosMusicais.add("Pop Punk");
        ListaEstilosMusicais.add("Pop Rock");
        ListaEstilosMusicais.add("Post-Hardcore");
        ListaEstilosMusicais.add("R&B");
        ListaEstilosMusicais.add("Punk");
        ListaEstilosMusicais.add("Punk Rock");
        ListaEstilosMusicais.add("Rap");
        ListaEstilosMusicais.add("Rap Rock");
        ListaEstilosMusicais.add("Rap Metal");
        ListaEstilosMusicais.add("Reggae");
        ListaEstilosMusicais.add("Rock and Roll");
        ListaEstilosMusicais.add("Rock Alternativo");
        ListaEstilosMusicais.add("Rock Eletrônico");
        ListaEstilosMusicais.add("Rock Progressivo");
        ListaEstilosMusicais.add("Rock Sinfônico");
        ListaEstilosMusicais.add("Ska");
        ListaEstilosMusicais.add("Speed Metal");
        ListaEstilosMusicais.add("Skate Rock");
        ListaEstilosMusicais.add("Thrash Metal");
    }
    public void show_list2(){
        final ListView list2 = findViewById(R.id.lista_2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Lista_MeuInstrumentos);
        list2.setAdapter(arrayAdapter);
    }

    public void abrirPag_MENU(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
