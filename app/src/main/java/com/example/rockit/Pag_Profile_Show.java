package com.example.rockit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rockit.Bandas.Pag_List_Your_Band;
import com.example.rockit.Classes.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pag_Profile_Show extends AppCompatActivity {
    DatabaseReference reference;

    ListView list;
    TextView textView;
    ArrayList<String> arrayText = new ArrayList<>();
    ArrayList<String> arrayText2 = new ArrayList<>();
    ArrayList<String> arrayText3 = new ArrayList<>();
    String userID;
    //Numero de whats e instagram do usuario;
    String number,instagramAccount="default";

    //FOTO data
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_profile_show);

        //Pega o ID do usuario do amigo da conversa
        Intent intent = getIntent(); //vem do arquivo Pag_message
        userID = intent.getStringExtra("userID");

        //FIREBASE
        fireBaseDataUser();

        //FOTO
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

    }

    public void onBackPressed(){
        super.onBackPressed();

    }

    public String cidade_user(String userLatitude, String userLongitude){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        double lat = Double.parseDouble(userLatitude);
        double lon = Double.parseDouble(userLongitude);
        try {
            List<Address> addresses = geocoder.getFromLocation(lat,lon, 1);
            String yourCity = addresses.get(0).getAddressLine(0);

            if(yourCity.contains("-")) {
                String subString = yourCity.split("-")[1];//Rua Saude,56 - Vila Aurora, São Paulo - SP, 123523-23
                return subString.split(",")[1];//Vila Aurora, São Paulo
            }else return yourCity;
        }
        catch(IOException e) {e.printStackTrace();}
        return "";
    }

    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    public void fireBaseDataUser(){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                if (user != null) {

                    //FOTO DO PERFIL
                    ImageView fotoUsuario = findViewById(R.id.im_user);
                    if(user.getImageURL().equals("default")){
                        fotoUsuario.setImageResource(R.drawable.foto_pessoa);
                    }else{
                        Glide.with(getApplicationContext()).load(user.getImageURL()).into(fotoUsuario);
                    }

                    //LOCALIZAÇÃO
                    String cidade="";
                    //Se o usuario nao permitiu acesso ao GPS, ou se nao quer mostrar
                    if(snapshot.hasChild("latitude")||snapshot.child("permission_location").getValue().equals("0")){
                        cidade = cidade_user(user.getLatitude(),user.getLongitude());
                    }
                    TextView texto = findViewById(R.id.textViewCidade);
                    texto.setText(cidade);

                    //Name User
                    textView = findViewById(R.id.nome_usuario);
                    textView.setText(user.getName());
                    //Age User
                    textView = findViewById(R.id.textViewAge);
                    textView.setText(user.getAge());
                    //Sex User
                    textView = findViewById(R.id.textViewSex);
                    textView.setText(user.getSex());
                    //Description
                    textView=findViewById(R.id.textDescription);
                    textView.setText(user.getDescription());
                    //WHATSAPP
                    number = user.getContact();
                    if(number == null) {
                        ImageView image = findViewById(R.id.im_whatsapp);
                        image.setVisibility(View.INVISIBLE);
                        texto = findViewById(R.id.textMeuWhatsapp);
                        texto.setVisibility(View.INVISIBLE);
                    }
                    //Instagram
                    instagramAccount = user.getInstagram();
                            //Se não tiver conta no Instagram
                    if(instagramAccount == null) {
                        ImageView image = findViewById(R.id.im_insta);
                        image.setVisibility(View.INVISIBLE);
                        texto = findViewById(R.id.textMeuInstagram);
                        texto.setVisibility(View.INVISIBLE);
                    }

                    //Lista Fav Bands
                    list = findViewById(R.id.listFavBands);
                    arrayText.clear();
                    arrayText.add(user.getBandas());
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayText);
                    list.setAdapter(arrayAdapter);

                    //Lista Instruments
                    ListView list2 = findViewById(R.id.listInstruments);
                    arrayText2.clear();
                    arrayText2.add(user.getInstrumentos());
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayText2);
                    list2.setAdapter(arrayAdapter2);

                    //Lista Fav Genres
                    ListView list3 = findViewById(R.id.listFavGenres);
                    arrayText3.clear();
                    arrayText3.add(user.getGeneros());
                    ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayText3);
                    list3.setAdapter(arrayAdapter3);


                    //Switch Search Band
                    if(user.getSearching_bands().equals("1")){
                        textView=findViewById(R.id.textSearchBand);
                        textView.setText("SIM");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
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


    public void openWhatsapp(View view){
        //String number = "351 931180989";

        String url = "https://api.whatsapp.com/send?phone=+" + number;
        try {
            PackageManager pm = getApplicationContext().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
