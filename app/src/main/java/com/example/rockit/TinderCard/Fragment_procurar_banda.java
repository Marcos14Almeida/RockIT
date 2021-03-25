package com.example.rockit.TinderCard;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rockit.Classes.Cards_Tinder;
import com.example.rockit.Classes.Generos;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class Fragment_procurar_banda extends Fragment {
///////////////////////////////////////////////
    //TINDER SWAP
//https://www.youtube.com/watch?v=SJW_4UMXbu8
//https://github.com/Diolor/Swipecards
    //part 7 - Register Swipes to the Database
    //https://www.youtube.com/watch?v=H5b0LSVRAeM&list=PLxabZQCAe5fio9dm1Vd0peIY6HLfo5MCf&index=9
///////////////////////////////////////////////
    ArrayList<Double> usuario1GeneroMedia= new ArrayList<Double>();;
    ArrayList<Double> usuario2GeneroMedia= new ArrayList<Double>();;
    int ngenres=23;

    private ArrayAdapter_Tinder_Card arrayAdapter;
    List<Cards_Tinder> rowItems;
    private DatabaseReference cardUserDB;
    private FirebaseAuth mAuth;
    private String currentUId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragmento_procurar_banda, container, false);

        mAuth = FirebaseAuth.getInstance();
        cardUserDB = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUId = mAuth.getCurrentUser().getUid();

        //inicializa variaveis da media
        for(int i=0;i<ngenres;i++){
            usuario1GeneroMedia.add(0.0);
            usuario2GeneroMedia.add(0.0);
        }

        //users from firebase
        readUsers();

        rowItems = new ArrayList<Cards_Tinder>();


        arrayAdapter = new ArrayAdapter_Tinder_Card(Objects.requireNonNull(getActivity()), R.layout.card_tinder, rowItems );

        SwipeFlingAdapterView flingContainer = view.findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Cards_Tinder cards_tinder = (Cards_Tinder) dataObject;
                String userID = cards_tinder.getUserID();
                cardUserDB.child(userID).child("connections").child("no").child(currentUId).setValue(true);

                Toast.makeText(getActivity(), "Nope", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards_Tinder cards_tinder = (Cards_Tinder) dataObject;
                String userID = cards_tinder.getUserID();
                cardUserDB.child(userID).child("connections").child("yes").child(currentUId).setValue(true);
                //Cria o não só pra garantir que apaga caso o usuario esteja mudando de opiniao, sem travar o app
                cardUserDB.child(userID).child("connections").child("no").child(currentUId).setValue(true);
                cardUserDB.child(userID).child("connections").child("no").child(currentUId).removeValue();
                Toast.makeText(getActivity(), "Like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //rowItems.add("Pessoa ".concat(String.valueOf(i)));
                //arrayAdapter.notifyDataSetChanged();
                //Log.d("LIST", "notified");
                //i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                Toast.makeText(getActivity(), "onScroll", Toast.LENGTH_SHORT).show();
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private String cidade_user(Usuario user){

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        double lat = Double.parseDouble(user.getLatitude());
        double lon = Double.parseDouble(user.getLongitude());
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
                    //Se não for o meu usuario mostra na lista
                    if(!oneUser.getId().equals(firebaseUser.getUid())) {//getImageURL->GETid!!!
                        //Se eu ainda nao/   dei like no outro usuario
                        if(!dataSnapshot.child("connections").child("yes").hasChild(currentUId)){

                            ////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////
                            //           SIMILARIDADE DE GOSTOS MUSICAIS              //
                            ////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////
                            //Get genres USUARIO 2
                            for(int i=0;i<ngenres;i++) {
                                String s = String.valueOf(dataSnapshot.child("genres").child(getGenreName(i)).getValue());
                                usuario2GeneroMedia.set(i,Double.parseDouble(s));
                            }

                            //Get genres USUARIO 1
                            if(snapshot.child(firebaseUser.getUid()).hasChild("genres")){
                                Log.d("resul0",usuario1GeneroMedia.toString());
                                for (int i = 0; i < ngenres; i++) {
                                    String s = String.valueOf(snapshot.child(firebaseUser.getUid()).child("genres").child(getGenreName(i)).getValue());
                                    usuario1GeneroMedia.set(i,Double.parseDouble(s));
                                }
                            }

                            //Log.d("resul1",usuario1GeneroMedia.toString());
                            //Log.d("resul2",usuario2GeneroMedia.toString());

                            //FORMULA E CALCULO DO RESULTADO ENTRE 0 E 100%
                            Double dResultadoFinal = similarityResult(usuario1GeneroMedia,usuario2GeneroMedia);
                            @SuppressLint("DefaultLocale") String resultadoFinal = String.format("%.2f", dResultadoFinal)+ '%';


                            //POR FIM COM TODAS AS INFOS
                            //Cria o objeto do cartao do Tinder
                            Cards_Tinder cards_tinder = new Cards_Tinder(
                                    dataSnapshot.getKey(),
                                    dataSnapshot.child("name").getValue().toString(),
                                    dataSnapshot.child("instrumentos").getValue().toString(),
                                    dataSnapshot.child("imageURL").getValue().toString(),
                                    dataSnapshot.child("generos").getValue().toString(),
                                    dataSnapshot.child("age").getValue().toString(),
                                    cidade_user(oneUser),
                                    dataSnapshot.child("searching_bands").getValue().toString(),
                                    resultadoFinal
                            );
                            rowItems.add(cards_tinder);


                            arrayAdapter.notifyDataSetChanged();

                        }}
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    /////////////////////////////////////////////////////////////
    //                    PONTUCAO SIMILARIDADE                //
    /////////////////////////////////////////////////////////////
    public Double similarityResult(ArrayList<Double> usuario1GeneroMedia, ArrayList<Double> usuario2GeneroMedia) {
        Double probs_genres = 0.0;
        Double soma_genres = 0.0;

        for (int i = 0; i < usuario1GeneroMedia.size(); i++){
            probs_genres = probs_genres + similarityFormula( usuario1GeneroMedia.get(i),usuario2GeneroMedia.get(i) );
            soma_genres = soma_genres + usuario1GeneroMedia.get(i)+usuario2GeneroMedia.get(i);
        }

        return 100*probs_genres/soma_genres;
    }


    private double similarityFormula(double media1, double media2){
        double standardDeviation =  ((media1-media2)*(media1-media2))   /2.0;
        standardDeviation = Math.sqrt(     standardDeviation);
        NormalDistribution ndist = new NormalDistribution();

        double result=ndist.cumulativeProbability(standardDeviation);
        result = 1.0 - (2.0*(result-0.5));
        result = result*(media1+media2);

        return result;
    }


    public String getGenreName(int i){
        if(i==0){return "alternative";}
        if(i==1){return "axe";}
        if(i==2){return "blues";}
        if(i==3){return "disco";}
        if(i==4){return "eletronica";}
        if(i==5){return "forro";}
        if(i==6){return "funk";}
        if(i==7){return "funky_americano";}
        if(i==8){return "hard_rock";}
        if(i==9){return "heavy_metal";}
        if(i==10){return "hip_hop";}
        if(i==11){return "jazz";}
        if(i==12){return "mpb";}
        if(i==13){return "opera";}
        if(i==14){return "pagode";}
        if(i==15){return "pop";}
        if(i==16){return "power_metal";}
        if(i==17){return "punk";}
        if(i==18){return "rap";}
        if(i==19){return "reggae";}
        if(i==20){return "rock_classico";}
        if(i==21){return "samba";}
        if(i==22){return "sertanejo";}
        //else
        return "0";
    }
}
