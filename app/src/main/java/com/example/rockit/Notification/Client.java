package com.example.rockit.Notification;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//https://www.youtube.com/watch?v=wDpxBTjvPys&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=18
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
