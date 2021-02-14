package com.example.rockit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pag_perfil_usuario extends AppCompatActivity {

    DatabaseHelper db=new DatabaseHelper(this);
    Integer id;
    ListView list;
    TextView textView;
    String texto;
    Switch aSwitch;
    ArrayList<String> arrayText = new ArrayList<>();
    ArrayList<String> arrayText2 = new ArrayList<>();
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
        textView = findViewById(R.id.textViewSex);
        textView.setText(db.getSex(id));

        //Description
        editDescription=findViewById(R.id.textDescription);
        editDescription.setText( db.getDescription(id));


        //Lista Fav Bands
        list = findViewById(R.id.listFavBands);
        arrayText.clear();
        arrayText.add(db.getFavBands(id));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayText);
        list.setAdapter(arrayAdapter);

        //Lista Instruments
        ListView list2 = findViewById(R.id.listInstruments);
        arrayText2.clear();
        arrayText2.add(db.getInstruments(id));
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayText2);
        list2.setAdapter(arrayAdapter2);

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



    }

    public void Editar_conteudo(View view){
        Toast.makeText(this,"Editar",Toast.LENGTH_SHORT).show();
    }
    public void Editar_Fav_bandas(View view){
        Intent intent = new Intent(this, Pag1_bandas_preferidas.class);
        startActivity(intent);
    }
    public void Editar_Instruments(View view){
        Intent intent = new Intent(this, Pag1_qual_intrumento_toca.class);
        startActivity(intent);
    }
}
