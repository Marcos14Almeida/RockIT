package com.example.rockit.Cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rockit.Classes.Usuario;
import com.example.rockit.MainActivity;
import com.example.rockit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Pag1_qual_intrumento_toca extends AppCompatActivity {
    ArrayList<String> ListaInstrumentos = new ArrayList<>();
    ArrayList<String> Lista_MeuInstrumentos = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView texto;
    AutoCompleteTextView searchViewBands;String selecionado;
    String currentInstruments;
    boolean sair=false;//corrige um bug que quando sai da pag, a lista2 fica com itens duplicados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_qual_intrumento_toca);

        texto = findViewById(R.id.textView49);
        texto.setText("");
        func_Lista_Instrumentos();

        //ve se o usuario atual ja toca instrumento
        readUsers();

        show_list();

        //SEARCH VIEW
        searchViewBands=findViewById(R.id.autoCompleteTextView);
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,ListaInstrumentos);
        searchViewBands.setAdapter(adapter);

    }
    ////////                FUNÇÕES                      /////////
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
        //Organiza por Ordem Alfabética
        Collections.sort(ListaInstrumentos, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        final ListView list = findViewById(R.id.lista);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, ListaInstrumentos);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
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

        if(!sair) {
            final ListView list2 = findViewById(R.id.lista_2);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, Lista_MeuInstrumentos);
            list2.setAdapter(arrayAdapter);
            //Se clicar deleta o item selecionado
            list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickedItem = (String) list2.getItemAtPosition(position);
                    Lista_MeuInstrumentos.remove(clickedItem);
                    ListaInstrumentos.add(clickedItem);
                    //show lista 2
                    arrayAdapter.notifyDataSetChanged();

                    if(adapter!=null){
                        adapter.notifyDataSetChanged();}
                }

            });
        }
    }
    public void search_icon(View view){
        selecionado = searchViewBands.getEditableText().toString();
        //If selecionado is on the list
        for(int i=0; i<ListaInstrumentos.size();i++){
            if(ListaInstrumentos.get(i).equals(selecionado)){
                Lista_MeuInstrumentos.add(selecionado);
                ListaInstrumentos.remove(selecionado);
            }
        }
        searchViewBands.setText("");
        show_list2();
    }
    ////////                PROXIMA PAG                      /////////
    public void abrirMain(View view){
        sair=true;
        //UPDATE DATABASE
        //Arraylist to String
        String string = "";
        for (String s : Lista_MeuInstrumentos){string += s + "; ";}



        //Se não toca nenhum instrumento
        if(Lista_MeuInstrumentos.size()==0){
            //Salva as infos no Firebase
            acesso("instrumentos"," ");
        }else {
            //Salva as infos no Firebase
            acesso("instrumentos",string);
        }

        acesso("first_login","true");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void acesso(String field, String string){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> map = new HashMap<>();
        map.put(field, string);
        reference.updateChildren(map);
    }
    private void readUsers(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Usuario oneUser = dataSnapshot.getValue(Usuario.class);
                    //garante que tem algum valor disponível
                    assert oneUser != null;
                    assert firebaseUser != null;


                    //Se não for o meu usuario mostra na lista de chats
                    if(oneUser.getId().equals(firebaseUser.getUid())) {

                        currentInstruments = oneUser.getInstrumentos();

                        if(currentInstruments.length()>2) {
                            currentInstruments = currentInstruments.replaceAll("\\s+", " "); //remove spaces between words
                            //NOME DOS MEMBROS DE LISTA PARA STRING
                            if (currentInstruments.contains(";")) {
                                String[] separated = currentInstruments.split(";");
                                Lista_MeuInstrumentos.addAll(Arrays.asList(separated));
                                Lista_MeuInstrumentos.remove(Lista_MeuInstrumentos.size() - 1);
                            }
                            //REMOVE FROM FULL LIST OF BANDS
                            for (int i = 0; i < Lista_MeuInstrumentos.size(); i++) {
                                Lista_MeuInstrumentos.set(i, Lista_MeuInstrumentos.get(i).trim());//remove espaços
                                for (int k = 0; k < ListaInstrumentos.size(); k++) {
                                    if (ListaInstrumentos.get(k).equals(Lista_MeuInstrumentos.get(i))) {
                                        ListaInstrumentos.remove(k);
                                        break;
                                    }
                                }
                            }
                            show_list2();

                        }
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
