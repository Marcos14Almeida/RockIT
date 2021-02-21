package com.example.rockit.Cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockit.DatabaseHelper;
import com.example.rockit.R;

import java.util.ArrayList;

public class Pag1_bandas_preferidas extends AppCompatActivity {

    DatabaseHelper db=new DatabaseHelper(this);
    ArrayList<String> ListaBandas = new ArrayList<>();
    ArrayList<String> Lista_MeuBandas = new ArrayList<>();
    TextView texto;
    AutoCompleteTextView searchViewBands;String selecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_bandas_preferidas);

        texto = findViewById(R.id.textView51);
        texto.setText("");
        func_Lista_Bandas();
        show_list();

        //SEARCH VIEW
        searchViewBands=findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,ListaBandas);
        searchViewBands.setAdapter(adapter);

    }

    ////////                FUNÇÕES                      /////////
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

                alerta(clickedItem);
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

    public void alerta(String item){
                Dialog rankDialog = new Dialog(Pag1_bandas_preferidas.this, R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.rank_dialog);
                rankDialog.setCancelable(true);
                RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating(5);

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                text.setText(item);

                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //nota = ratingBar.getRating();
                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();
    }


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
        //UPDATE DATABASE
        //Arraylist to String
        String string = "";
        for (String s : Lista_MeuBandas){string += s + ";\t";}
        db.updateFavBands(1,string);
        Intent intent = new Intent(this, Pag1_qual_intrumento_toca.class);
        startActivity(intent);
    }

}
