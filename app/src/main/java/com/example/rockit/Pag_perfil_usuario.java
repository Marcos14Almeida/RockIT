package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pag_perfil_usuario extends AppCompatActivity {

    DatabaseHelper db=new DatabaseHelper(this);
    AutoCompleteTextView autoCompleteTextView;
    Integer id;
    ListView list;
    TextView textView;
    String texto;
    Switch aSwitch;
    ArrayList<String> arrayText = new ArrayList<>();
    ArrayList<String> arrayText2 = new ArrayList<>();
    ArrayList<String> arrayText3 = new ArrayList<>();
    EditText editDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_perfil_usuario);


         id = 1;

        //Name User
        textView = findViewById(R.id.nome_usuario);
        textView.setText(db.getName(id));

        //Age User
        textView = findViewById(R.id.textViewAge);
        textView.setText(db.getAge(id));

        //Sex User
        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewSex);
        autoCompleteTextView.setText(db.getSex(id));

        //Description
        editDescription=findViewById(R.id.textDescription);
        editDescription.setText( db.getDescription(id));

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
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayText2);
        list2.setAdapter(arrayAdapter2);

        //Lista Fav Genres
        ListView list3 = findViewById(R.id.listFavGenres);
        arrayText3.clear();
        arrayText3.add(db.getFavGenres(id));
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayText3);
        list3.setAdapter(arrayAdapter3);

        //Switch Search Band
        aSwitch = findViewById(R.id.switch1);
        if(db.getSearchingBand(id).equals("1")){
            aSwitch.setChecked(true);
        }
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    db.updateSearchingBand(id,"1");
                }else{
                    db.updateSearchingBand(id,"0");
                }
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

            String subString = yourCity.split("\\-")[1];//Rua Saude,56 - Vila Aurora, São Paulo - SP, 123523-23
            return subString.split("\\,")[1];//Vila Aurora, São Paulo
        }
        catch(IOException e) {e.printStackTrace();}
        return "";
    }

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
