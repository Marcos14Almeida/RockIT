package com.example.rockit.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.example.rockit.Pag_configuracoes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Classe_Geral {

    public String Data(String date){
        int hour = Integer.parseInt(date.substring(0,2));
        int minute = Integer.parseInt(date.substring(3,5));
        int day = Integer.parseInt(date.substring(6,8));
        int month = Integer.parseInt(date.substring(9,11));
        int year = Integer.parseInt(date.substring(12,16));

        //Horário atual
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("HH");
        String horaAtual = df.format(Calendar.getInstance().getTime());
        Integer intHoraAtual = Integer.parseInt(horaAtual);

        df = new SimpleDateFormat("dd");
        String diaAtual = df.format(Calendar.getInstance().getTime());
        Integer intDiaAtual = Integer.parseInt(diaAtual);

        df = new SimpleDateFormat("MM");
        String mesAtual = df.format(Calendar.getInstance().getTime());
        Integer intMesAtual = Integer.parseInt(mesAtual);

        df = new SimpleDateFormat("yyyy");
        String anoAtual = df.format(Calendar.getInstance().getTime());
        Integer intAnoAtual = Integer.parseInt(anoAtual);

        String mes="";
        if(month==1){mes="Janeiro";}
        if(month==2){mes="Fevereiro";}
        if(month==3){mes="Março";}
        if(month==4){mes="Abril";}
        if(month==5){mes="Maio";}
        if(month==6){mes="Junho";}
        if(month==7){mes="Julho";}
        if(month==8){mes="Agosto";}
        if(month==9){mes="Setembro";}
        if(month==10){mes="Outubro";}
        if(month==11){mes="Novembro";}
        if(month==12){mes="Dezembro";}

        String minuteString = String.valueOf(minute);
        if(minute<10){
            minuteString= "0"+minuteString;
        }

        if(intDiaAtual==day){
            return "Hoje - " + hour +":"+ minuteString;
        }else{
            if(intAnoAtual ==  year){
                return day + " de " + mes;
            }else {
                return day + " de " + mes + " de " + year;
            }
        }

    }

    public String cidade_user(Context context, String userLatitude, String userLongitude){
        if(userLatitude.equals("")){
        return "";
        }

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
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
    public String filterBands(DataSnapshot snapshot){
        String s= "";
        for (DataSnapshot dataSnapshot: snapshot.child("bands").getChildren()){
            s+=dataSnapshot.getKey()+"; "; //getKey = pega o nome da banda e não o valor da nota
        }
        return s;
    }

    public String filterGeneros(DataSnapshot snapshotUser){
        //Pega os generos do usuario e filtra pela nota de cada um
        ArrayList<String> generos = new ArrayList<>();
        ArrayList<Double> generosNotas = new ArrayList<>();
        for(DataSnapshot dataSnapshot: snapshotUser.child("genres").getChildren()){
            if(Double.parseDouble(dataSnapshot.getValue().toString())>1){
                generosNotas.add(Double.parseDouble(dataSnapshot.getValue().toString()));
                generos.add(dataSnapshot.getKey());
            }
        }
        //Organiza os generos pela nota
        String auxiliarString;
        Double auxiliarDouble;
        for(int i=0;i<generosNotas.size();i++){
            for(int k=i;k<generosNotas.size();k++) {
                if(generosNotas.get(k)>generosNotas.get(i)){
                    auxiliarString=generos.get(i);generos.set(i,generos.get(k));generos.set(k,auxiliarString);
                    auxiliarDouble=generosNotas.get(i);generosNotas.set(i,generosNotas.get(k));generosNotas.set(k,auxiliarDouble);
                }
            }
        }
        auxiliarString="";
        for(int i=0;i<generosNotas.size();i++){
            auxiliarString += generos.get(i)+"; ";
        }
        return auxiliarString;
    }
    public ArrayList filterGenerosArray(DataSnapshot snapshotUser){
        //Pega os generos do usuario e filtra pela nota de cada um
        ArrayList<String> generos = new ArrayList<>();
        ArrayList<Double> generosNotas = new ArrayList<>();
        for(DataSnapshot dataSnapshot: snapshotUser.child("genres").getChildren()){
            if(Double.parseDouble(dataSnapshot.getValue().toString())>1){
                generosNotas.add(Double.parseDouble(dataSnapshot.getValue().toString()));
                generos.add(dataSnapshot.getKey());
            }
        }
        //Organiza os generos pela nota
        String auxiliarString;
        Double auxiliarDouble;
        for(int i=0;i<generosNotas.size();i++){
            for(int k=i;k<generosNotas.size();k++) {
                if(generosNotas.get(k)>generosNotas.get(i)){
                    auxiliarString=generos.get(i);generos.set(i,generos.get(k));generos.set(k,auxiliarString);
                    auxiliarDouble=generosNotas.get(i);generosNotas.set(i,generosNotas.get(k));generosNotas.set(k,auxiliarDouble);
                }
            }
        }
        return generos;
    }
    /////////////////////////////////////////////////////////////
    //                    F I R E B A S E                      //
    /////////////////////////////////////////////////////////////

    //troca dados no database
    public void updateFieldUsers(String field, String string){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> map = new HashMap<>();
        map.put(field, string);
        reference.updateChildren(map);
    }

    public void updateFieldUsersReference(String field, String string, DatabaseReference reference){
        HashMap<String, Object> map = new HashMap<>();
        map.put(field, string);
        reference.updateChildren(map);
    }
    public void deleteFieldUsers(String field){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.child(field).removeValue();
    }

}


