package com.example.rockit.Notification;

//https://www.youtube.com/watch?v=wDpxBTjvPys&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=18
public class Data {
    private String user;
    private String message;
    private String title;
    private String sented;

    public Data( String sented, String user, String title, String message) {
        this.sented = sented;
        this.user = user;
        this.title = title;
        this.message = message;
    }

    public Data(){}

    public String getUser() {        return user;    }
    public void setUser(String user) {        this.user = user;    }

}
