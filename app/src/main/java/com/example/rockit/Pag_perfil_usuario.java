package com.example.rockit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockit.Cadastro.Pag1_bandas_preferidas;
import com.example.rockit.Cadastro.Pag1_genero_musical;
import com.example.rockit.Cadastro.Pag1_qual_intrumento_toca;
import com.example.rockit.Classes.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pag_perfil_usuario extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    DatabaseHelper db=new DatabaseHelper(this);
    AutoCompleteTextView autoCompleteTextView;
    Integer id;
    ListView list;
    TextView textView;
    Switch aSwitch;
    ArrayList<String> arrayText = new ArrayList<>();
    ArrayList<String> arrayText2 = new ArrayList<>();
    ArrayList<String> arrayText3 = new ArrayList<>();
    EditText editDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_perfil_usuario);

        //FIREBASE
        fireBaseDataUser();

         id = 1;

        //Lista Fav Bands
        list = findViewById(R.id.listFavBands);
        arrayText.clear();
        arrayText.add(db.getFavBands(id));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayText);
        list.setAdapter(arrayAdapter);

        //Lista Instruments
        ListView list2 = findViewById(R.id.listInstruments);
        arrayText2.clear();
        arrayText2.add(db.getInstruments(id));
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayText2);
        list2.setAdapter(arrayAdapter2);

        //Lista Fav Genres
        ListView list3 = findViewById(R.id.listFavGenres);
        arrayText3.clear();
        arrayText3.add(db.getFavGenres(id));
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayText3);
        list3.setAdapter(arrayAdapter3);

        //Switch Search Band
        aSwitch = findViewById(R.id.switch1);
        if(db.getSearchingBand(id).equals("1")){
            aSwitch.setChecked(true);
        }
        aSwitch.setOnClickListener(v -> {
            if(aSwitch.isChecked()){
                db.updateSearchingBand(id,"1");
            }else{
                db.updateSearchingBand(id,"0");
            }
        });

        //LOCALIZAÇÃO
        String cidade = cidade_user();
        TextView texto = findViewById(R.id.textViewCidade);
        texto.setText(cidade);


    }

    public String cidade_user(){
        //Display Latitude para teste
        DatabaseHelper db=new DatabaseHelper(this);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        double lat = Double.parseDouble(db.getLocationLatitude(1));
        double lon = Double.parseDouble(db.getLocationLongitude(1));
        try {
            List<Address> addresses = geocoder.getFromLocation(lat,lon, 1);
            String yourCity = addresses.get(0).getAddressLine(0);

            String subString = yourCity.split("-")[1];//Rua Saude,56 - Vila Aurora, São Paulo - SP, 123523-23
            return subString.split(",")[1];//Vila Aurora, São Paulo
        }
        catch(IOException e) {e.printStackTrace();}
        return "";
    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void fireBaseDataUser(){
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        Usuario user = snapshot.getValue(Usuario.class);
        if (user != null) {

        //Name User
        textView = findViewById(R.id.nome_usuario);
        textView.setText(user.getName());

        //Age User
        textView = findViewById(R.id.textViewAge);
        textView.setText(user.getAge());

        //Sex User
        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewSex);
        autoCompleteTextView.setText(user.getSex());

        //Description
        editDescription=findViewById(R.id.textDescription);
        editDescription.setText(user.getDescription());

        if(user.getImageURL().equals("default")){
            ImageView fotoUsuario = findViewById(R.id.im_user);
            fotoUsuario.setImageResource(R.drawable.foto_pessoa);
        }
        else{
            ImageView fotoUsuario = findViewById(R.id.im_user);
            fotoUsuario.setImageResource(R.drawable.foto_usuario);
        }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
    }



    /////////////////////////////////////////////////////////////
    //                     F U N Ç Õ E S                       //
    /////////////////////////////////////////////////////////////
    public void Salvar_conteudo(View view){
        String selecionado = editDescription.getEditableText().toString();
        db.updateDescription(id,selecionado);
        Toast.makeText(this,"Informações Salvas",Toast.LENGTH_SHORT).show();
    }
    public void Editar_conteudo(View view){
        Toast.makeText(this,"Editar",Toast.LENGTH_SHORT).show();
    }
    public void Editar_Fav_Genres(View view){
        Intent intent = new Intent(this, Pag1_genero_musical.class);
        startActivity(intent);
    }
    public void Editar_Fav_Bandas(View view){
        Intent intent = new Intent(this, Pag1_bandas_preferidas.class);
        startActivity(intent);
    }
    public void Editar_Instruments(View view){
        Intent intent = new Intent(this, Pag1_qual_intrumento_toca.class);
        startActivity(intent);
    }
}
