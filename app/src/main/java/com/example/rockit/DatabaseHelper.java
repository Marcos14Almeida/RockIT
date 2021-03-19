package com.example.rockit;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class DatabaseHelper extends SQLiteOpenHelper  {

    SQLiteDatabase db;

    private static final String DATABASE_NAME = "usua.db";
    private static final String TABLE_NAME = "_usua";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEX = "sex";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_AGE = "age";
    private static final String KEY_STARS = "stars";
    private static final String KEY_NUMBER_FOLLOWERS = "number_followers";
    private static final String KEY_LOCATION_LATITUDE = "location_latitude";
    private static final String KEY_LOCATION_LONGITUDE = "location_longitude";
    private static final String KEY_SEARCHING_BAND = "searching_band";//0 ou 1
    private static final String KEY_FAV_GENRES = "fav_genres";
    private static final String KEY_FAV_BANDS = "fav_bands";
    private static final String KEY_INSTRUMENTS = "instruments";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_EXTRAINFO = "extra";
    String[] columns = new String[]{KEY_ID,KEY_NAME,KEY_SEX,KEY_DESCRIPTION,KEY_AGE,KEY_STARS,KEY_NUMBER_FOLLOWERS,
            KEY_LOCATION_LATITUDE,KEY_LOCATION_LONGITUDE,KEY_SEARCHING_BAND,KEY_FAV_GENRES,KEY_FAV_BANDS,KEY_INSTRUMENTS,KEY_EMAIL,KEY_EXTRAINFO};

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME +" TEXT, "
                + KEY_SEX +" TEXT, "
                + KEY_DESCRIPTION +" TEXT, "
                + KEY_AGE +" TEXT, "
                + KEY_STARS +" TEXT, "
                + KEY_NUMBER_FOLLOWERS +" TEXT, "
                + KEY_LOCATION_LATITUDE +" TEXT, "
                + KEY_LOCATION_LONGITUDE +" TEXT, "
                + KEY_SEARCHING_BAND +" TEXT, "
                + KEY_FAV_GENRES +" TEXT, "
                + KEY_FAV_BANDS +" TEXT, "
                + KEY_INSTRUMENTS +" TEXT, "
                + KEY_EMAIL +" TEXT, "
                + KEY_EXTRAINFO +" TEXT);"
                ;
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //                                 A D D   D A T A                                      //
    //Add User to table
    public boolean addData(String name,String sex,String description,String age,String stars,
                           String number_followers,String latitude,String longitude,String searching_band,
                           String fav_genres,String fav_bands,String instruments,String email,String extra) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_SEX, sex);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_AGE, age);
        values.put(KEY_STARS, stars);
        values.put(KEY_NUMBER_FOLLOWERS, number_followers);
        values.put(KEY_LOCATION_LATITUDE, latitude);
        values.put(KEY_LOCATION_LONGITUDE, longitude);
        values.put(KEY_SEARCHING_BAND, searching_band);
        values.put(KEY_FAV_GENRES, fav_genres);
        values.put(KEY_FAV_BANDS, fav_bands);
        values.put(KEY_INSTRUMENTS, instruments);
        values.put(KEY_EMAIL, email);
        values.put(KEY_EXTRAINFO, extra);

        long result = db.insert(TABLE_NAME, null, values);

        //if date as inserted incorrectly it will return -1
        return result != -1;  //return true if result != -1
    }


    public boolean addName(String value) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, value);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;  //return true if result != -1
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //                             G E T   A L L (String)                                   //
    public String printData(){
        db = this.getReadableDatabase();
        Cursor cursor =db.query(TABLE_NAME,columns,null,null,null,null,null);
        String result = "";
        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result=result+"Id: "+cursor.getString(cursor.getColumnIndex(KEY_ID))+"\n"
                         +"Name: "+cursor.getString(cursor.getColumnIndex(KEY_NAME))+"\n"
                         +"Sexo: "+cursor.getString(cursor.getColumnIndex(KEY_SEX))+"\n"
                         +"Idade: "+cursor.getString(cursor.getColumnIndex(KEY_AGE))+"\n"
                         +"Descrição: "+cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))+"\n"
                         //+"Estrelas: "+cursor.getString(cursor.getColumnIndex(KEY_STARS))+"\n"
                         //+"Latitude: "+cursor.getString(cursor.getColumnIndex(KEY_LOCATION_LATITUDE))+"\n"
                         //+"Longitude: "+cursor.getString(cursor.getColumnIndex(KEY_LOCATION_LONGITUDE))+"\n"
                    +"searching_band: "+cursor.getString(cursor.getColumnIndex(KEY_SEARCHING_BAND))+"\n"
                    +"fav_genres: "+cursor.getString(cursor.getColumnIndex(KEY_FAV_GENRES))+"\n"
                    +"instruments: "+cursor.getString(cursor.getColumnIndex(KEY_INSTRUMENTS))+"\n"
                    +"email: "+cursor.getString(cursor.getColumnIndex(KEY_EMAIL))+"\n"
                    +"fav_bands: "+cursor.getString(cursor.getColumnIndex(KEY_FAV_BANDS))+"\n\n"
            ;
        }

        db.close();
        return result;
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //                              G E T   V A L U E (String)                              //
    public String getName(long id){return getItem(id,1);}
    public String getSex(long id){return getItem(id,2);}
    public String getDescription(long id){return getItem(id,3);}
    public String getAge(long id){return getItem(id,4);}
    public String getStars(long id){return getItem(id,5);}
    public String getNumberFollowers(long id){return getItem(id,6);}
    public String getLocationLatitude(long id){return getItem(id,7);}
    public String getLocationLongitude(long id){return getItem(id,8);}
    public String getSearchingBand(long id){return getItem(id,9);}
    public String getFavGenres(long id){return getItem(id,10);}
    public String getFavBands(long id){return getItem(id,11);}
    public String getInstruments(long id){return getItem(id,12);}
    public String getEmail(long id){return getItem(id,13);}
    public String getExtraInfo(long id){return getItem(id,14);}

    public String getItem(long id,int n){//id=-1 último valor inserido            com n>=1 n= posicao do nome(1), email, descricao latitude etc...
        db = this.getReadableDatabase();
        if(id>-1) {
            Cursor cursor = db.query(TABLE_NAME, columns, KEY_ID + "=" + id, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String name = cursor.getString(n);
                return name;
            }
        }
        if(id==-1) {
            Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
            if (cursor.moveToLast()) {
                String name = cursor.getString(n);
                return name;
            }
        }
       return null;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //                                 U P D A T E                                         //

    public void updateName(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_NAME, variable);
        updateValue(id,values);
    }
    public void updateSex(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_SEX, variable);
        updateValue(id,values);
    }
    public void updateDescription(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_DESCRIPTION, variable);
        updateValue(id,values);
    }
    public void updateAge(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_AGE, variable);
        updateValue(id,values);
    }
    public void updateStars(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_STARS, variable);
        updateValue(id,values);
    }
    public void updateNumberFollowers(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_NUMBER_FOLLOWERS, variable);
        updateValue(id,values);
    }
    public void updateLocationLatitude(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_LOCATION_LATITUDE, variable);
        updateValue(id,values);
    }
    public void updateLocationLongitude(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_LOCATION_LONGITUDE, variable);
        updateValue(id,values);
    }
    public void updateSearchingBand(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_SEARCHING_BAND, variable);
        updateValue(id,values);
    }
    public void updateFavGenres(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_FAV_GENRES, variable);
        updateValue(id,values);
    }
    public void updateFavBands(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_FAV_BANDS, variable);
        updateValue(id,values);
    }
    public void updateInstruments(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_INSTRUMENTS, variable);
        updateValue(id,values);
    }
    public void updateEmail(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_EMAIL, variable);
        updateValue(id,values);
    }
    public void updateExtraInfo(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_EXTRAINFO, variable);
        updateValue(id,values);
    }

    public void updateValue(long id, ContentValues values){
        db = this.getWritableDatabase();
        db.update(TABLE_NAME,values,KEY_ID+"="+id,null);
        db.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //                                 D E L E T E                                          //
    public void deleteName(long l){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"="+l,null);
    }

    public void deleteTable(){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

}
