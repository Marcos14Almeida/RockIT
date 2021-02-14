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

public class Pag1_bandas_preferidas extends AppCompatActivity {

    ArrayList<String> ListaBandas = new ArrayList<>();
    ArrayList<String> Lista_MeuBandas = new ArrayList<>();
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_bandas_preferidas);

        texto = findViewById(R.id.textView51);
        texto.setText("");
        func_Lista_Bandas();
        show_list();

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
    public void show_list(){
        final ListView list = findViewById(R.id.lista_bandas);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, ListaBandas);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(Pag1_bandas_preferidas.this,clickedItem,Toast.LENGTH_LONG).show();
                Lista_MeuBandas.add(clickedItem);
                ListaBandas.remove(clickedItem);
                //show lista 2
                show_list2();
            }
        });
    }
    public void show_list2(){
        //Titulo da pag: Meus intrumentos praticados
        if(Lista_MeuBandas.size()>0){
            texto.setText(getResources().getString(R.string.bandas_favoritas));
        }else{texto.setText("");}

        final ListView list2 = findViewById(R.id.lista_2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, Lista_MeuBandas);
        list2.setAdapter(arrayAdapter);
        //Se clicar deleta o item selecionado
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list2.getItemAtPosition(position);
                Lista_MeuBandas.remove(clickedItem);
                ListaBandas.add(clickedItem);
                //show lista 2
                show_list2();
            }
        });
    }
    ////////                FUNÇÕES                      /////////
    public void abrirPag(View view){
        Intent intent = new Intent(this, Pag1_qual_intrumento_toca.class);
        startActivity(intent);
    }
}
