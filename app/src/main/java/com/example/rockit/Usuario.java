package com.example.rockit;

public class Usuario {

    //Usado no fragment Show
    private int mphoto;
    private String fullname;
    private String age;
    private String email;

    //used in fragmento shows para mostrar scroll da pagina
    public Usuario(String Username, String Userage, String Useremail, int photo) {
        this.mphoto = photo;
        this.fullname = Username;
        this.age = Userage;
        this.email = Useremail;
    }

    public int getPhoto() {
        return mphoto;
    }
    public String getName() {
        return fullname;
    }
    public String getAge() {
        return age;
    }
    public String getDescription() {
        return email;
    }
}

