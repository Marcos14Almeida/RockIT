package com.example.rockit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseBand extends SQLiteOpenHelper{

    SQLiteDatabase db;

    private static final String DATABASE_NAME = "band.db";
    private static final String TABLE_NAME = "_band";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_AGE = "age";
    private static final String KEY_STARS = "stars";
    private static final String KEY_NUMBER_FOLLOWERS = "number_followers";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_INSTRUMENT_NEEDED = "instrument_needed";
    private static final String KEY_SEARCHING_MEMBER = "searching_member";//0 ou 1
    private static final String KEY_FAV_GENRES = "fav_genres";
    private static final String KEY_FAV_BANDS = "fav_bands";
    private static final String KEY_INSTRUMENTS = "instruments";
    String[] columns = new String[]{KEY_ID,KEY_NAME,KEY_CONTACT,KEY_DESCRIPTION,KEY_AGE,KEY_STARS,KEY_NUMBER_FOLLOWERS,
            KEY_LOCATION,KEY_INSTRUMENT_NEEDED,KEY_SEARCHING_MEMBER,KEY_FAV_GENRES,KEY_FAV_BANDS,KEY_INSTRUMENTS};

    public DatabaseBand(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME +" TEXT, "
                + KEY_CONTACT +" TEXT, "
                + KEY_DESCRIPTION +" TEXT, "
                + KEY_AGE +" TEXT, "
                + KEY_STARS +" TEXT, "
                + KEY_NUMBER_FOLLOWERS +" TEXT, "
                + KEY_LOCATION +" TEXT, "
                + KEY_INSTRUMENT_NEEDED +" TEXT, "
                + KEY_SEARCHING_MEMBER +" TEXT, "
                + KEY_FAV_GENRES +" TEXT, "
                + KEY_FAV_BANDS +" TEXT, "
                + KEY_INSTRUMENTS +" TEXT);"
                ;
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add User to table
    public boolean addData(String name,String contact,String description,String age,String stars,
                           String number_followers,String location,String instrument_needed,String searching_member,String fav_genres,String fav_bands,String instruments) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_CONTACT, contact);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_AGE, age);
        values.put(KEY_STARS, stars);
        values.put(KEY_NUMBER_FOLLOWERS, number_followers);
        values.put(KEY_LOCATION, location);
        values.put(KEY_INSTRUMENT_NEEDED, instrument_needed);
        values.put(KEY_SEARCHING_MEMBER, searching_member);
        values.put(KEY_FAV_GENRES, fav_genres);
        values.put(KEY_FAV_BANDS, fav_bands);
        values.put(KEY_INSTRUMENTS, instruments);
        //Log.d(name, "addData: Adding " + name + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, values);

        //if date as inserted incorrectly it will return -1
        return result != -1;  //return true if result != -1
    }

    /**
     * Returns all the data from database
     * return
     */
    public String getData(){
        db = this.getReadableDatabase();
        Cursor cursor =db.query(TABLE_NAME,columns,null,null,null,null,null);
        String result = "";
        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result=result+"Id: "+cursor.getString(cursor.getColumnIndex(KEY_ID))+"\n"
                    +"Name: "+cursor.getString(cursor.getColumnIndex(KEY_NAME))+"\n"
                    +"Idade: "+cursor.getString(cursor.getColumnIndex(KEY_AGE))+"\n"
                    +"Genres: "+cursor.getString(cursor.getColumnIndex(KEY_FAV_GENRES))+"\n"
            ;
        }

        db.close();
        return result;
    }

    /*
     * Returns only the ID that matches the name passed in param name
     * return
     */
    public String getName(long id){return getItem(id,1);}
    public String getContact(long id){return getItem(id,2);}
    public String getDescription(long id){return getItem(id,3);}
    public String getAge(long id){return getItem(id,4);}
    public String getStars(long id){return getItem(id,5);}
    public String getNumberFollowers(long id){return getItem(id,6);}
    public String getLocation(long id){return getItem(id,7);}
    public String getInstrumentNeeded(long id){return getItem(id,8);}
    public String getSearchingMember(long id){return getItem(id,9);}
    public String getFavGenres(long id){return getItem(id,10);}
    public String getFavBands(long id){return getItem(id,11);}
    public String getInstruments(long id){return getItem(id,12);}

    public String getItem(long id,int n){
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,columns,KEY_ID+"="+id,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            String name=cursor.getString(n);
            return name;
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
    public void updateContact(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_CONTACT, variable);
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
    public void updateLocation(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_LOCATION, variable);
        updateValue(id,values);
    }
    public void updateInstrumentNeeded(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_LOCATION, variable);
        updateValue(id,values);
    }
    public void updateSearchingMember(long id, String variable){
        ContentValues values=new ContentValues();
        values.put(KEY_SEARCHING_MEMBER, variable);
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
