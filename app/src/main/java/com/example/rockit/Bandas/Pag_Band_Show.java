package com.example.rockit.Bandas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.rockit.Classes.Banda;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.MainActivity;
import com.example.rockit.Pag_Profile_Show;
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
import java.util.HashMap;

//O VIDEO ESTA PUXANDO O LAYOUT PRA BAIXO POR ALGUM MOTIVO
public class Pag_Band_Show extends AppCompatActivity {
    String userID="";
    String bandID;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    //VideoView video;
    //ImageView buttonplay;
    ListView list;
    ArrayList<String> listMyMembers = new ArrayList<>();
    MediaController mediaController;
    RatingBar ratingBar;
    String instagramAccount="default";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_band_show);

        //buttonplay = findViewById(R.id.buttonplay);
        //video = findViewById(R.id.videoView);
        mediaController = new MediaController(this);

        //Toolbar / barra superior
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Se clicar no botao de retornar
        toolbar.setNavigationOnClickListener(v -> finish());

        //Pega o ID do nome da banda
        Intent intent = getIntent(); //vem do arquivo Recycler View Menu
        bandID = intent.getStringExtra("userID");

        //Dados da página
        fireBaseDataBand();
    }


    //ANTES DE FECHAR
    //SALVA A NOTA DAS ESTRELAS DADAS
    public void onDestroy() {
        super.onDestroy();
        ratingBar=findViewById(R.id.ratingBar_banda);
        float stars = ratingBar.getRating();
        //atualiza no firebase
        if(stars>0.0){
        updateFieldUsers("stars",String.valueOf(stars));}
    }


    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void fireBaseDataBand(){
        //INICIA FIREBASE
        //recebe o userID do item selecionado
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Bands").child(bandID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Banda band = snapshot.getValue(Banda.class);
                assert band != null;

                //Define o nome do usuario no topo da pagina
                TextView username=findViewById(R.id.nameBand);
                username.setText(band.getName());
                //Descrição
                username=findViewById(R.id.texto_descricao);
                username.setText(band.getDescription());
                //GENEROS
                username=findViewById(R.id.textGeneros);
                username.setText(band.getGeneros());
                //Cidade
                username=findViewById(R.id.textCity);
                username.setText(band.getCity());
                //MEMBROS
                list = findViewById(R.id.list_members);
                listMyMembers.clear();
                listMyMembers.addAll(Arrays.asList(band.getMembers().split(";")   ));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listMyMembers);
                list.setAdapter(arrayAdapter);
                //CLICA NO NOME DO MEMBRO
                //*Quando retorna do profile o setOnItemClickListener continua ativo
                list.setOnItemClickListener((parent, view, position, id) -> {
                    String nomeUser=(String) list.getItemAtPosition(position);
                    getFirebaseIDfromUser(nomeUser);
                });

                //Instagram
                instagramAccount = band.getInstagram();
                //Se não tiver conta no Instagram
                if(instagramAccount.length()<2) {
                    ImageView image = findViewById(R.id.im_insta);
                    image.setVisibility(View.INVISIBLE);
                }else{
                    instagramAccount = band.getInstagram();
                }

                //Youtube
                if(!snapshot.hasChild("youtube")){
                    ImageView image = findViewById(R.id.im_youtube);
                    image.setVisibility(View.INVISIBLE);
                }

                ///////////////////////////////////////////////////////////////
                //FOTOS
                if (band.getImageURL().equals("default")) {
                    //FOTO NO MENU PRINCIPAL
                    ImageView fotoUsuario = findViewById(R.id.im_banda);
                    fotoUsuario.setImageResource(R.drawable.foto_banda_generica);
                }else {
                    //FOTO NO MENU PRINCIPAL
                    ImageView fotoUsuario = findViewById(R.id.im_banda);
                    Glide.with(getApplicationContext()).load(band.getImageURL()).into(fotoUsuario);
                }

                //ratingBar
                ratingBar=findViewById(R.id.ratingBar_banda2);
                ratingBar.setRating(Float.parseFloat(band.getStars()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getFirebaseIDfromUser(String nomeUser) {
        //INICIA FIREBASE
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Usuario oneUser = dataSnapshot.getValue(Usuario.class);

                    assert oneUser != null;
                    if (oneUser.getName().equals(nomeUser)) {
                        userID=oneUser.getId();
                    }
                }
                //Mostra o perfil do usuario quando clica no nome dele na lista
                if(!userID.equals("")) {
                    //Intent intent = new Intent(getApplicationContext(), Pag_Profile_Show.class);
                    //intent.putExtra("userID", userID);
                    //startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    //Update Stars
    //troca dados no database
    public void updateFieldUsers(String field, String string){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bands").child(bandID);
        HashMap<String, Object> map = new HashMap<>();
        map.put(field, string);
        reference.updateChildren(map);
    }

    public void openInstagram(View view){
        Uri uri = Uri.parse("http://instagram.com/_u/" + instagramAccount);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/" + instagramAccount)));
        }

    }


}