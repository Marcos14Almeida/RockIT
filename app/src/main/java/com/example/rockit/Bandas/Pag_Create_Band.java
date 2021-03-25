package com.example.rockit.Bandas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import java.util.HashMap;



public class Pag_Create_Band extends AppCompatActivity {
    EditText editName,editYear,editTextCity;
    String name,year,city;
    ProgressBar progressBar;
    ArrayList<String> ListaCidades = new ArrayList<>();

    //Lista de membros
    AutoCompleteTextView autoCompleteTextView,autoCompleteCity;
    String members="";
    ArrayList<String> listMyMembers = new ArrayList<>();
    ArrayList<String> listMembers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_create_band);

        //Procura se meu usuario tem alguma banda associada
        //minhaBanda();

        //lista do nome das cidades disponíveis
        func_Lista_Cidades();

        editName = findViewById(R.id.editTextName);
        editYear = findViewById(R.id.editTextAno);
        //SEARCH VIEW
        autoCompleteCity=findViewById(R.id.AutoCompleteCity);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,ListaCidades);
        autoCompleteCity.setAdapter(adapter1);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //SEARCH VIEW
        autoCompleteTextView=findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,listMembers);
        autoCompleteTextView.setAdapter(adapter2);

        //Pega nome de todos os usuarios
        readUsers();//firebase

        show_list_members();

    }

    public void func_Lista_Cidades() {
        ListaCidades.add("São Paulo");
        ListaCidades.add("Campinas");
        ListaCidades.add("Guarulhos");
        ListaCidades.add("Buaru");
        ListaCidades.add("Osasco");
        ListaCidades.add("Santo André");
        ListaCidades.add("Ribeirão Preto");
        ListaCidades.add("Americana");
        ListaCidades.add("São Caetano");
        ListaCidades.add("Limeira");
        ListaCidades.add("Piracicaba");
        ListaCidades.add("Catanduva");
        ListaCidades.add("Barueri");
        ListaCidades.add("Cubatão");
        ListaCidades.add("Mogi das Cruzes");
        ListaCidades.add("Jundiaí");
        ListaCidades.add("Indaiatuba");
        ListaCidades.add("Santos");
        ListaCidades.add("Francisco Morato");
        ListaCidades.add("Valinhos");
        ListaCidades.add("Marília");
        ListaCidades.add("Mairiporã");
        ListaCidades.add("Embu das Artes");
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////// OTHER FUNCTIONS /////////////////////////////
    public void button_menu_principal(View view){
        name = editName.getText().toString();
        year = editYear.getText().toString();
        city = autoCompleteCity.getEditableText().toString();

        if(!name.equals("") && !year.equals("") && !city.equals("")){


            //NOME DOS MEMBROS DE LISTA PARA STRING
            for(int i=0; i<listMyMembers.size();i++){
                members+= listMyMembers.get(i)+";";
            }

            if(Integer.parseInt(year)<1960 || Integer.parseInt(year)>2021){
                Toast.makeText(this,"Ano Inválido",Toast.LENGTH_LONG).show();
                 }else{
                progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                RegisterUser();
            }
        }
        else{
            Toast.makeText(this,"Fields empty",Toast.LENGTH_LONG).show();
        }

    }
    public void show_list_members(){
        //SHOW LIST MEMBERS
        ListView list = findViewById(R.id.list_members);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.extra_list_text_white, listMyMembers);
        list.setAdapter(arrayAdapter);
    }
    public void button_add_Members(View view){
        String nameMember = autoCompleteTextView.getEditableText().toString();
        //If selecionado is on the list
        for(int i=0; i<listMembers.size();i++){
            if(listMembers.get(i).equals(nameMember)){
                listMyMembers.add(nameMember);
                listMembers.remove(nameMember);
                show_list_members();
            }
        }
        autoCompleteTextView.setText("");
    }

    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void RegisterUser(){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                if (user != null) {

                            //String userID = firebaseUser.getUid();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bands").child(name);

                            // Sign in success, update UI with the signed-in user's information
                            //Verificar se esses campos estão contido tambem na classe correspondente
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name",name);
                            map.put("year",year);
                            map.put("city",city);
                            map.put("members",members);
                            map.put("description","");
                            map.put("imageURL","default");
                            map.put("searching_members","0");
                            map.put("stars","0");
                            map.put("instagram","");
                            map.put("number_followers","0");
                            // Write a message to the database
                            reference.setValue(map).addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Authentication Success.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Authentication Fail.",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "No connection to Firebase.",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {            }
                });
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
                    if(!oneUser.getId().equals(firebaseUser.getUid())) {
                        listMembers.add(oneUser.getName());
                    }
                    else{
                        listMyMembers.add(oneUser.getName());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
