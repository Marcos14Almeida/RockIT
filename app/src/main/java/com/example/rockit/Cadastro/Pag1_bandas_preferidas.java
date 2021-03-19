package com.example.rockit.Cadastro;

import androidx.annotation.NonNull;
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

import com.example.rockit.Classes.Usuario;
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

public class Pag1_bandas_preferidas extends AppCompatActivity {

    ArrayList<String> ListaBandas = new ArrayList<>();
    ArrayList<String> Lista_MeuBandas = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView texto;
    AutoCompleteTextView searchViewBands;String selecionado;
    String currentBands;
    boolean sair=false; //corrige um bug que quando sai da pag, a lista2 fica com itens duplicados
    boolean UserExists=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag1_bandas_preferidas);

        //pega lista de todas as bandas
        texto = findViewById(R.id.textView51);
        texto.setText("");
        func_Lista_Bandas();

        //ve se o usuario atual ja gosta de alguma banda
        readUsers();

        show_list();

        //SEARCH VIEW
        searchViewBands=findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,ListaBandas);
        searchViewBands.setAdapter(adapter);

    }

    ////////                FUNÇÕES                      /////////
    public void func_Lista_Bandas(){
        ListaBandas.add("ACDC");
        ListaBandas.add("Aerosmith");
        ListaBandas.add("AFI");
        ListaBandas.add("Alice In Chains");
        ListaBandas.add("Alter Bridge");
        ListaBandas.add("Amaranthe");
        ListaBandas.add("Anti-Flag");
        ListaBandas.add("Arctic Monkeys");
        ListaBandas.add("Asking Alexandria");
        ListaBandas.add("AVATAR");
        ListaBandas.add("Avenged Sevenfold");
        ListaBandas.add("Avril Lavigne");
        ListaBandas.add("Audioslave");
        ListaBandas.add("The Beatles");
        ListaBandas.add("Badflower");
        ListaBandas.add("Bad Religion");
        ListaBandas.add("Beartooth");
        ListaBandas.add("Beastie Boys");
        ListaBandas.add("Billy Idol");
        ListaBandas.add("Billy Talent");
        ListaBandas.add("Black Veil Brides");
        ListaBandas.add("Bon Jovi");
        ListaBandas.add("Black Sabbath");
        ListaBandas.add("Blink-182");
        ListaBandas.add("Bring Me The Horizon");
        ListaBandas.add("Bullet for My Valentine");
        ListaBandas.add("Bush");
        ListaBandas.add("Cage the Elephant");
        ListaBandas.add("Charlie Brown Jr.");
        ListaBandas.add("Claudia Leite");
        ListaBandas.add("CPM22");
        ListaBandas.add("Dead Sara");
        ListaBandas.add("Diamante");
        ListaBandas.add("Disturbed");
        ListaBandas.add("Dragonforce");
        ListaBandas.add("Dropkick Murphys");
        ListaBandas.add("Drowning Bodies");
        ListaBandas.add("Ego Kill Talent");
        ListaBandas.add("Egypt Central");
        ListaBandas.add("Elvis Presley");
        ListaBandas.add("Epica");
        ListaBandas.add("Evanescence");
        ListaBandas.add("Fall Out Boy");
        ListaBandas.add("Far From Alaska");
        ListaBandas.add("Foo Fighters");
        ListaBandas.add("Fresno");
        ListaBandas.add("Ghost");
        ListaBandas.add("Grandson");
        ListaBandas.add("Green Day");
        ListaBandas.add("Guns N' Roses");
        ListaBandas.add("Halestorm");
        ListaBandas.add("Highly Suspect");
        ListaBandas.add("Holywood Undead");
        ListaBandas.add("James Brown");
        ListaBandas.add("Johnny Cash");
        ListaBandas.add("Judas Priest");
        ListaBandas.add("Kasabian");
        ListaBandas.add("Kiss");
        ListaBandas.add("K.Flay");
        ListaBandas.add("Imagine Dragons");
        ListaBandas.add("In Flames");
        ListaBandas.add("I Prevail");
        ListaBandas.add("Iron Maiden");
        ListaBandas.add("Jimi Hendrix");
        ListaBandas.add("Killswitch Engage");
        ListaBandas.add("Korn");
        ListaBandas.add("Lacuna Coil");
        ListaBandas.add("Led Zeppelin");
        ListaBandas.add("Linkin Park");
        ListaBandas.add("Machine Gun Kelly");
        ListaBandas.add("Mamonas Assassinas");
        ListaBandas.add("Maroon 5");
        ListaBandas.add("Mastodon");
        ListaBandas.add("Megadeth");
        ListaBandas.add("Metallica");
        ListaBandas.add("Moptop");
        ListaBandas.add("Motionless In White");
        ListaBandas.add("Motley Crue");
        ListaBandas.add("Motorhead");
        ListaBandas.add("MUSE");
        ListaBandas.add("My Chemical Romance");
        ListaBandas.add("New Found Glory");
        ListaBandas.add("Nirvana");
        ListaBandas.add("No Doubt");
        ListaBandas.add("NOFX");
        ListaBandas.add("NX Zero");
        ListaBandas.add("Offspring");
        ListaBandas.add("Palaye Royale");
        ListaBandas.add("Pantera");
        ListaBandas.add("Papa Roach");
        ListaBandas.add("Paramore");
        ListaBandas.add("Pearl Jam");
        ListaBandas.add("Pennywise");
        ListaBandas.add("Pierce the Veil");
        ListaBandas.add("Pitty");
        ListaBandas.add("Queen");
        ListaBandas.add("Queens of the Stone Age");
        ListaBandas.add("Quiet Riot");
        ListaBandas.add("Raimundos");
        ListaBandas.add("Ramones");
        ListaBandas.add("Rammstein");
        ListaBandas.add("Rise Against");
        ListaBandas.add("Rage Against the Machine");
        ListaBandas.add("Red Hot Chili Peppers");
        ListaBandas.add("Restart");
        ListaBandas.add("Rival Sons");
        ListaBandas.add("Royal Blood");
        ListaBandas.add("Scatolove");
        ListaBandas.add("Seether");
        ListaBandas.add("Sepultura");
        ListaBandas.add("Sex Pistols");
        ListaBandas.add("Skank");
        ListaBandas.add("Shinedown");
        ListaBandas.add("Slayer");
        ListaBandas.add("Slipknot");
        ListaBandas.add("Smash Mouth");
        ListaBandas.add("Soundgarden");
        ListaBandas.add("Sum41");
        ListaBandas.add("Stone Temple Pilots");
        ListaBandas.add("Story Untold");
        ListaBandas.add("Strike");
        ListaBandas.add("System of a Down");
        ListaBandas.add("Tenacious D");
        ListaBandas.add("The Pretty Reckless");
        ListaBandas.add("The Strokes");
        ListaBandas.add("The Rolling Stones");
        ListaBandas.add("The Smashing Pumpkins");
        ListaBandas.add("The White Stripes");
        ListaBandas.add("The Who");
        ListaBandas.add("Tool");
        ListaBandas.add("Twisted Sister");
        ListaBandas.add("U2");
        ListaBandas.add("Underoath");
        ListaBandas.add("Van Halen");
        ListaBandas.add("Velvet Revolver");
        ListaBandas.add("While She Sleeps");
        ListaBandas.add("Whitesnake");
        ListaBandas.add("Wolfmother");
        ListaBandas.add("Yonaka");
        ListaBandas.add("ZZ Top");

        ListaBandas.add("Amy Winehouse");
        ListaBandas.add("Anitta");
        ListaBandas.add("Roberto Carlos");
        ListaBandas.add("Michael Jackson");
        ListaBandas.add("Madonna");
        ListaBandas.add("Ivete Sangalo");
        ListaBandas.add("Run DMC");
        ListaBandas.add("Beyonce");
        ListaBandas.add("Bob Marley");
        ListaBandas.add("Britney Spears");
        ListaBandas.add("Bruno Mars");
        ListaBandas.add("Claudia Leite");
        ListaBandas.add("Michel Telo");
        ListaBandas.add("Luan Santana");
        ListaBandas.add("Chitãozinho e Chororó");
        ListaBandas.add("Eminem");
    }
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
        sair=true;
        //UPDATE DATABASE
        //Arraylist to String
        String string = "";
        for (String s : Lista_MeuBandas){string += s + ";\t";}



        if(Lista_MeuBandas.size()==0){
            sair=false;
            Toast.makeText(getApplicationContext(),"Selecione pelo menos 1 item",Toast.LENGTH_SHORT).show();
        }else {
            //Salva as infos no Firebase
                    acesso(string);

                    Intent intent = new Intent(this, Pag1_qual_intrumento_toca.class);
                    startActivity(intent);

        }
    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void acesso(String string){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> map = new HashMap<>();
        map.put("bandas", string);
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

                        currentBands = oneUser.getBandas();

                        if(currentBands.length()>2) {
                            currentBands = currentBands.replaceAll("\\s+", " "); //remove spaces between words
                            //NOME DOS MEMBROS DE LISTA PARA STRING
                            if (currentBands.contains(";")) {
                                String[] separated = currentBands.split(";");
                                Lista_MeuBandas.addAll(Arrays.asList(separated));
                                Lista_MeuBandas.remove(Lista_MeuBandas.size() - 1);
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
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
