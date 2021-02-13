package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Pag1_genero_musical extends AppCompatActivity {

    ArrayList<String> ListaEstilosMusicais = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_genero_musical);

        func_Lista_Estilos_Musicais();
        //SHOW LIST
        final ListView list = findViewById(R.id.lista_generos);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, ListaEstilosMusicais);
        list.setAdapter(arrayAdapter);

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

    public void abrir_Pag_bandas_favoritas(View view){
        Intent intent = new Intent(this, Pag1_bandas_preferidas.class);
        startActivity(intent);
    }
}
