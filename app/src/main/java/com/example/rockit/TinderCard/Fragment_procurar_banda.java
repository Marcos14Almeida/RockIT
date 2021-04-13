package com.example.rockit.TinderCard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rockit.Cadastro.Pag0_login;
import com.example.rockit.Classes.Classe_Geral;
import com.example.rockit.Classes.Cards_Tinder;
import com.example.rockit.Classes.Generos;
import com.example.rockit.Classes.Usuario;
import com.example.rockit.Pag_Profile_Show;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

///////////////////////////////////////////////
//TINDER SWAP
//https://www.youtube.com/watch?v=SJW_4UMXbu8
//https://github.com/Diolor/Swipecards
//part 7 - Register Swipes to the Database
//https://www.youtube.com/watch?v=H5b0LSVRAeM&list=PLxabZQCAe5fio9dm1Vd0peIY6HLfo5MCf&index=9
///////////////////////////////////////////////

public class Fragment_procurar_banda extends Fragment {

    Classe_Geral classe_geral = new Classe_Geral();

    ArrayList<Double> usuario1GeneroMedia= new ArrayList<Double>();
    ArrayList<Double> usuario2GeneroMedia= new ArrayList<Double>();
    ArrayList<String> bandsInCommon= new ArrayList<String>();

    private ArrayAdapter_Tinder_Card arrayAdapter;
    List<Cards_Tinder> rowItems;
    private DatabaseReference cardUserDB;
    private FirebaseAuth mAuth;
    private String currentUId, new_match;
    int ngenres;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragmento_procurar_banda, container, false);

        mAuth = FirebaseAuth.getInstance();
        cardUserDB = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUId = mAuth.getCurrentUser().getUid();

        Generos generos = new Generos();
        ngenres = generos.getNumberGenres();
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
                //Toast.makeText(getActivity(), "onScroll", Toast.LENGTH_SHORT).show();
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                //Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();

                Cards_Tinder cards_tinder = (Cards_Tinder) dataObject;
                String userID = cards_tinder.getUserID();
                Intent intent = new Intent(getContext(), Pag_Profile_Show.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });


        return view;
    }

    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////
    private void readUsers(){
        //dataSnapshot = usuario 2 = perfil mostrado no tinder
        //snapshot = usuario 1 =pessoa usando o app
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
                    //Log.d("ID",dataSnapshot.child("id").getValue().toString());
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
                                Log.d("saida",s);
                                usuario2GeneroMedia.set(i,Double.parseDouble(s));
                            }

                            //bandas em comun
                            bandsInCommon.clear();
                            for(DataSnapshot bandsSnapshot2: dataSnapshot.child("bands").getChildren()){//user2 bands
                                for(DataSnapshot bandsSnapshot1: snapshot.child(firebaseUser.getUid()).child("bands").getChildren()){//user bands

                                    if(Objects.equals(bandsSnapshot1.getKey(), bandsSnapshot2.getKey())) {
                                        bandsInCommon.add(bandsSnapshot1.getKey());
                                    }
                                }
                            }

                            //Get genres USUARIO 1
                            if(snapshot.child(firebaseUser.getUid()).hasChild("genres")){
                                for (int i = 0; i < ngenres; i++) {
                                    String s = String.valueOf(snapshot.child(firebaseUser.getUid()).child("genres").child(getGenreName(i)).getValue());
                                    usuario1GeneroMedia.set(i,Double.parseDouble(s));
                                }
                            }

                            //Log.d("resul1",usuario1GeneroMedia.toString());
                            //Log.d("resul2",usuario2GeneroMedia.toString());

                            //FORMULA E CALCULO DO RESULTADO ENTRE 0 E 100%
                            Double dResultadoFinal = similarityResult(usuario1GeneroMedia,usuario2GeneroMedia);
                            @SuppressLint("DefaultLocale") String SimilaridadeMusical = String.format("%.2f", dResultadoFinal)+ '%';



                                //calculo distancia entre usuarios
                            double distanceKM=100.0;
                                //Se o valor existir
                                if(dataSnapshot.hasChild("latitude") && snapshot.child(firebaseUser.getUid()).hasChild("latitude")) {
                                    distanceKM = distance(Double.parseDouble(oneUser.getLatitude()),
                                            Double.parseDouble(oneUser.getLongitude()),
                                            Double.parseDouble(String.valueOf(snapshot.child(firebaseUser.getUid()).child("latitude").getValue())),
                                            Double.parseDouble(String.valueOf(snapshot.child(firebaseUser.getUid()).child("longitude").getValue()))
                                    );
                                }
                                else{
                                oneUser.setLatitude("");
                                oneUser.setLongitude("");
                            }

                            //Se ja deslizou pra esquerda  ou não no outro usuario
                            if(dataSnapshot.child("connections").child("no").hasChild(currentUId)){
                                new_match = "NO";
                            }else{
                                new_match = "YES";
                            }

                            //POR FIM COM TODAS AS INFOS
                            //Cria o objeto do cartao do Tinder
                            Cards_Tinder cards_tinder = new Cards_Tinder(
                                    dataSnapshot.getKey(),
                                    dataSnapshot.child("name").getValue().toString(),
                                    dataSnapshot.child("instrumentos").getValue().toString(),
                                    classe_geral.filterBands(dataSnapshot),
                                    dataSnapshot.child("imageURL").getValue().toString(),
                                    classe_geral.filterGeneros(dataSnapshot),
                                    dataSnapshot.child("age").getValue().toString(),
                                    classe_geral.cidade_user(getContext(), oneUser.getLatitude(),oneUser.getLongitude()),
                                    dataSnapshot.child("searching_bands").getValue().toString(),
                                    SimilaridadeMusical,
                                    String.valueOf(distanceKM),
                                    new_match
                            );
                            rowItems.add(cards_tinder);

                            arrayAdapter.notifyDataSetChanged();

                        }}
                }//end: for datasnapshot...

                /////////////////////////////////////
                //Organiza as cartas de tinder
                for (int i = 0; i < rowItems.size(); i++) {
                    Cards_Tinder cards_tinder=rowItems.get(i);
                    double distanceKM = Double.parseDouble(cards_tinder.getDistanceKM());
                    for (int k = i; k < rowItems.size(); k++) {
                        cards_tinder=rowItems.get(k);
                        double distanceKM2 = Double.parseDouble(cards_tinder.getDistanceKM());

                        //Se o usuario estiver mais perto, sobe na lista
                        //Se ja deu nao pro outro usuario ele vai pro final da lista
                        if((distanceKM2<distanceKM) ||
                           (rowItems.get(i).getNewMatch().equals("NO") && rowItems.get(k).getNewMatch().equals("YES"))     ){
                            //troca os items
                            Cards_Tinder cards_auxiliar=rowItems.get(i);
                            rowItems.set(i,rowItems.get(k));
                            rowItems.set(k,cards_auxiliar);
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    /////////////////////////////////////////////////////////////
    //                    DISTANCIA USUARIOS                //
    /////////////////////////////////////////////////////////////
    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return Math.sqrt(distance);
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

    /////////////////////////////////////////////////////////////
    //                    OUTRO                                //
    /////////////////////////////////////////////////////////////
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
