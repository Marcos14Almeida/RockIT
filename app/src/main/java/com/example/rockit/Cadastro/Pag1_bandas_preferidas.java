package com.example.rockit.Cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockit.Classes.Classe_Geral;
import com.example.rockit.Classes.Generos;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Pag1_bandas_preferidas extends AppCompatActivity {
    Classe_Geral classe_geral = new Classe_Geral();
    ArrayList<Double> media = new ArrayList<Double>();
    ArrayList<Double> mediaFinal = new ArrayList<Double>();
    Double nbandsLiked=0.0;
    int ngenres;int sizeLista_MeuBandas=0;

    String first_login="false";

    ArrayList<String> ListaBandas = new ArrayList<>();
    ArrayList<String> Lista_MeuBandas = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView texto;
    AutoCompleteTextView searchViewBands;String selecionado;

    boolean sair=false; //corrige um bug que quando sai da pag, a lista2 fica com itens duplicados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_bandas_preferidas);

        Generos generos = new Generos();
        ngenres = generos.getNumberGenres();

        //pega lista de todas as bandas
        texto = findViewById(R.id.textView51);
        texto.setText("");
        //PEGA A LISTA DE BANDAS DO DATABASE
        ListaBandas();

        //ve se o usuario atual ja gosta de alguma banda
        readUsers();

        //SEARCH VIEW
        searchViewBands=findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,ListaBandas);
        searchViewBands.setAdapter(adapter);

    }

    ////////                FUNÇÕES                      /////////
    public void show_list(){
        //Organiza por Ordem Alfabética
        Collections.sort(ListaBandas, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        final ListView list = findViewById(R.id.lista_bandas);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, ListaBandas);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Lista_MeuBandas.add(clickedItem);
                ListaBandas.remove(clickedItem);
                arrayAdapter.notifyDataSetChanged();

                //AVALIA A BANDA DE 0-5 Estrelas
                //alerta(clickedItem);

                //show lista 2
                show_list2();

                if(adapter!=null){
                    adapter.notifyDataSetChanged();}
                    }
        });
    }
    public void show_list2(){
        //Titulo da pag: Meus intrumentos praticados
        if(Lista_MeuBandas.size()>0){
            texto.setText(getResources().getString(R.string.bandas_favoritas));
        }else{texto.setText("");}

        if(!sair){

        final ListView list2 = findViewById(R.id.lista_2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, Lista_MeuBandas);
        list2.setAdapter(arrayAdapter);
        //Vai pro final da lista
        list2.setSelection(Lista_MeuBandas.size() - 1);
        //Se clicar deleta o item selecionado
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list2.getItemAtPosition(position);
                Lista_MeuBandas.remove(clickedItem);
                ListaBandas.add(clickedItem);

                arrayAdapter.notifyDataSetChanged();
            }
        });
        }
    }

    //NOTA 1-5 ESTRELAS
    public void alerta(String item){
                Dialog rankDialog = new Dialog(Pag1_bandas_preferidas.this, R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.rank_dialog);
                rankDialog.setCancelable(true);
                RatingBar ratingBar = rankDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating(5);

                TextView text =  rankDialog.findViewById(R.id.rank_dialog_text1);
                text.setText(item);

                Button updateButton = rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(view -> {
                    //nota = ratingBar.getRating();
                    rankDialog.dismiss();
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();
    }


    ///////////////////////////////////////////////////////////////////////////////////
    //                         BOTAO DE ADICIONAR BANDA                              //
    public void search_icon(View view){
        selecionado = searchViewBands.getEditableText().toString();
        //If selecionado is on the list
        for(int i=0; i<ListaBandas.size();i++){
            if(ListaBandas.get(i).equals(selecionado)){
                Lista_MeuBandas.add(selecionado);
                ListaBandas.remove(selecionado);
            }
        }
        searchViewBands.setText("");
        show_list2();
    }
    ////////                PROXIMA PAG                      /////////
    public void abrirPag(View view){
        Toast.makeText(this,"Salvando...",Toast.LENGTH_SHORT).show();

        sair=true;

        if(Lista_MeuBandas.size()==0){
            sair=false;
            Toast.makeText(getApplicationContext(),"Selecione pelo menos 1 item",Toast.LENGTH_SHORT).show();
        }else {
            //Salva as infos no Firebase

            for(int i=0;i<ngenres;i++){
                media.add(0.0);
                mediaFinal.add(0.0);
            }
                    clearUserBand();
                    for (int i=0; i<Lista_MeuBandas.size();i++){
                        sizeLista_MeuBandas=Lista_MeuBandas.size();
                        //SALVA NOTA E NOME DA BANDA
                    relationUserBand(Lista_MeuBandas.get(i),"5");
                    //PEGA A NOTA DOS GENEROS
                        genre(Lista_MeuBandas.get(i));
                    }

        }
    }


    private void ListaBandas(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bandas");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ListaBandas.add(dataSnapshot.getKey());
                }
                show_list();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void readUsers(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Usuario oneUser = snapshot.getValue(Usuario.class);
                    //garante que tem algum valor disponível
                    assert oneUser != null;

                        first_login=oneUser.getFirst_login();

                        for (DataSnapshot dataSnapshot: snapshot.child("bands").getChildren()){
                            Lista_MeuBandas.add(dataSnapshot.getKey()); //getKey = pega o nome da banda e não o valor da nota atribuida a ela
                        }

                            //REMOVE FROM FULL LIST OF BANDS
                            for (int i = 0; i < Lista_MeuBandas.size(); i++) {
                                Lista_MeuBandas.set(i, Lista_MeuBandas.get(i).trim());//remove espaços
                                for (int k = 0; k < ListaBandas.size(); k++) {
                                    if (ListaBandas.get(k).equals(Lista_MeuBandas.get(i))) {
                                        ListaBandas.remove(k);
                                        break;
                                    }
                                }
                            }
                            show_list2();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void genre(String bandName){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bandas").child(bandName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Generos genres = snapshot.getValue(Generos.class);

                nbandsLiked++;
                //lista das bandas
                    //Nome da banda = procurado//verificar tipo e nome dos dados
                for(int i=0;i<ngenres;i++){
                    assert genres != null;
                    media.set(i,  media.get(i)+ genres.getGenreValue(i));
                }
                //No final pega a média e salva
                for(int i=0;i<ngenres;i++){
                    mediaFinal.set(i,  media.get(i)/nbandsLiked);
                }

                //Log.d("MEDIA",String.valueOf(mediaFinal));

                //No ultimo loop salva as infos no database
                if(nbandsLiked==sizeLista_MeuBandas){
                    for(int i=0;i<ngenres;i++){
                        @SuppressLint("DefaultLocale") String mediaFinalFormatada = String.format("%.2f", mediaFinal.get(i)).replaceAll(",",".");

                        //SALVA NO DATABASE O RESULTADO DA MEDIA FINAL POR GENERO
                        relationUserGenre(genres.getGenreName(i), mediaFinalFormatada);
                    }
                    if(first_login.equals("false")) {
                        Intent intent = new Intent(getApplicationContext(), Pag1_qual_intrumento_toca.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void clearUserBand(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> map = new HashMap<>();
        map.put("bands", " ");
        reference.updateChildren(map);
    }
    public void relationUserBand(String bandName,String stars){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("bands");
        HashMap<String, Object> map = new HashMap<>();
        map.put(bandName, stars);
        reference.updateChildren(map);
    }

    public void relationUserGenre(String genreName,String media){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("genres");
        HashMap<String, Object> map = new HashMap<>();
        map.put(genreName, media);
        reference.updateChildren(map);
    }

}
