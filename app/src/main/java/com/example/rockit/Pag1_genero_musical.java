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

public class Pag1_genero_musical extends AppCompatActivity {

    DatabaseHelper db=new DatabaseHelper(this);
    ArrayList<String> ListaEstilosMusicais = new ArrayList<>();
    ArrayList<String> Lista_MeuEstilosMusicais = new ArrayList<>();
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_genero_musical);

        texto = findViewById(R.id.textView50);
        texto.setText("");
        func_Lista_Estilos_Musicais();
        show_list();

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
    public void show_list(){
        final ListView list = findViewById(R.id.lista_generos);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, ListaEstilosMusicais);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(Pag1_genero_musical.this,clickedItem,Toast.LENGTH_LONG).show();
                Lista_MeuEstilosMusicais.add(clickedItem);
                ListaEstilosMusicais.remove(clickedItem);
                //Refresh list
                show_list2();
            }
        });
    }
    public void show_list2(){
        //Titulo da pag: Meus intrumentos praticados
        if(Lista_MeuEstilosMusicais.size()>0){
            texto.setText(getResources().getString(R.string.meus_generos));
        }else{texto.setText("");}

        final ListView list2 = findViewById(R.id.lista_2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, Lista_MeuEstilosMusicais);
        list2.setAdapter(arrayAdapter);
        //Se clicar deleta o item selecionado
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list2.getItemAtPosition(position);
                Lista_MeuEstilosMusicais.remove(clickedItem);
                ListaEstilosMusicais.add(clickedItem);
                //Refresh list
                show_list2();
            }
        });
    }
    public void abrir_Pag_bandas_favoritas(View view){
        //UPDATE DATABASE
        //Arraylist to String
        String string = "";
        for (String s : Lista_MeuEstilosMusicais){string += s + ";\t";}
        db.updateFavGenres(1,string);

        Intent intent = new Intent(this, Pag1_bandas_preferidas.class);
        startActivity(intent);
    }
}
