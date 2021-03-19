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
import android.widget.Toast;

import com.example.rockit.DatabaseHelper;
import com.example.rockit.MainActivity;
import com.example.rockit.R;
import com.example.rockit.Classes.Usuario;
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

public class Pag1_genero_musical extends AppCompatActivity {
    ArrayList<String> ListaEstilosMusicais = new ArrayList<>();
    ArrayList<String> Lista_MeuEstilosMusicais = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView texto;
    String currentGenres;
    AutoCompleteTextView searchViewBands;String selecionado;
    boolean sair=false;//corrige um bug que quando sai da pag, a lista2 fica com itens duplicados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_genero_musical);

        texto = findViewById(R.id.textView50);
        texto.setText("");
        func_Lista_Estilos_Musicais();

        //ve se o usuario atual ja gosta de alguma banda
        readUsers();

        show_list();

        //SEARCH VIEW
        searchViewBands=findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,ListaEstilosMusicais);
        searchViewBands.setAdapter(adapter);

    }



    ////////                FUNÇÕES                      /////////
    public void func_Lista_Estilos_Musicais() {
        ListaEstilosMusicais.add("Blues");
        ListaEstilosMusicais.add("Bossa-Nova");
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
        ListaEstilosMusicais.add("Samba");
        ListaEstilosMusicais.add("Sertanejo");
        ListaEstilosMusicais.add("Ska");
        ListaEstilosMusicais.add("Speed Metal");
        ListaEstilosMusicais.add("Skate Rock");
        ListaEstilosMusicais.add("Thrash Metal");
    }
    public void show_list(){
        //Organiza por Ordem Alfabética
        Collections.sort(ListaEstilosMusicais, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
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
                if(adapter!=null){
                adapter.notifyDataSetChanged();}
            }
        });
    }
    public void show_list2(){
        //Titulo da pag: Meus intrumentos praticados
        if(Lista_MeuEstilosMusicais.size()>0){
            texto.setText(getResources().getString(R.string.meus_generos));
        }else{texto.setText("");}

        if(!sair) {//corrige bug que duplica os nomes ao sair da página
            final ListView list2 = findViewById(R.id.lista_2);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, Lista_MeuEstilosMusicais);
            list2.setAdapter(arrayAdapter);
            //Se clicar deleta o item selecionado
            list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickedItem = (String) list2.getItemAtPosition(position);
                    Lista_MeuEstilosMusicais.remove(clickedItem);
                    ListaEstilosMusicais.add(clickedItem);
                    //Refresh list
                    arrayAdapter.notifyDataSetChanged();
                }
            });
        }
    }
    public void search_icon(View view){
        selecionado = searchViewBands.getEditableText().toString();
        //If selecionado is on the list
        for(int i=0; i<ListaEstilosMusicais.size();i++){
            if(ListaEstilosMusicais.get(i).equals(selecionado)){
                Lista_MeuEstilosMusicais.add(selecionado);
                ListaEstilosMusicais.remove(selecionado);
            }
        }
        searchViewBands.setText("");
        show_list2();
    }
    ////////                PROXIMA PAG                      /////////
    public void abrir_Pag_bandas_favoritas(View view){
        sair=true;
        //UPDATE DATABASE
        //Arraylist to String
        String string = "";
        for (String s : Lista_MeuEstilosMusicais){string += s + ";\t";}


        if(Lista_MeuEstilosMusicais.size()==0){
            sair=false;
            Toast.makeText(getApplicationContext(),"Selecione pelo menos 1 item",Toast.LENGTH_SHORT).show();
        }else {
            //Salva as infos no Firebase
            acesso(string);

            Intent intent = new Intent(this, Pag1_bandas_preferidas.class);
            startActivity(intent);
        }
    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void acesso(String string){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> map = new HashMap<>();
        map.put("generos", string);
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

                        currentGenres = oneUser.getGeneros();
                        if(currentGenres.length()>2) {
                            currentGenres = currentGenres.replaceAll("\\s+", " "); //remove spaces between words
                            //NOME DOS MEMBROS DE LISTA PARA STRING
                            if (currentGenres.contains(";")) {
                                String[] separated = currentGenres.split(";");
                                Lista_MeuEstilosMusicais.addAll(Arrays.asList(separated));
                                Lista_MeuEstilosMusicais.remove(Lista_MeuEstilosMusicais.size() - 1);
                            }
                            //REMOVE FROM FULL LIST OF BANDS
                            for (int i = 0; i < Lista_MeuEstilosMusicais.size(); i++) {
                                Lista_MeuEstilosMusicais.set(i, Lista_MeuEstilosMusicais.get(i).trim());//remove espaços
                                for (int k = 0; k < ListaEstilosMusicais.size(); k++) {
                                    if (ListaEstilosMusicais.get(k).equals(Lista_MeuEstilosMusicais.get(i))) {
                                        ListaEstilosMusicais.remove(k);
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
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

}
