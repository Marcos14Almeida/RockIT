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

public class Pag1_genero_musical extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference reff;
    Usuario usuario;
    DatabaseHelper db=new DatabaseHelper(this);
    ArrayList<String> ListaEstilosMusicais = new ArrayList<>();
    ArrayList<String> Lista_MeuEstilosMusicais = new ArrayList<>();
    TextView texto;
    AutoCompleteTextView searchViewBands;String selecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_genero_musical);

        texto = findViewById(R.id.textView50);
        texto.setText("");
        func_Lista_Estilos_Musicais();
        show_list();

        //SEARCH VIEW
        searchViewBands=findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,ListaEstilosMusicais);
        searchViewBands.setAdapter(adapter);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }



    ////////                FUNÇÕES                      /////////
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

    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void Registro(){
        //Objeto usuario com dados genericos
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(usuario);
    }

    public void acesso(){
        reff = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void AcessUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();


            Toast.makeText(Pag1_genero_musical.this,"Ex:"+name+"\n"+uid,Toast.LENGTH_LONG).show();
        }
    }
    ////////                PROXIMA PAG                      /////////
    public void abrir_Pag_bandas_favoritas(View view){
        //UPDATE DATABASE
        //Arraylist to String
        String string = "";
        for (String s : Lista_MeuEstilosMusicais){string += s + ";\t";}
        Usuario usuario;

        //acesso();
        //AcessUserInformation();
        //Registro();
        db.updateFavGenres(1,string);

        Intent intent = new Intent(this, Pag1_bandas_preferidas.class);
        startActivity(intent);
    }
}
