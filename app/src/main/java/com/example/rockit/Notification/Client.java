package com.example.rockit.Notification;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//https://www.youtube.com/watch?v=7Xc_5cduL-Y 8:30
public class Client {
    public static Retrofit retrofit = null;
    public static Retrofit getClient(String url){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
