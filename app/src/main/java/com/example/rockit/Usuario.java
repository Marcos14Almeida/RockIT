package com.example.rockit;

public class Usuario {

    private int mphoto;
    private String mname;
    private String mlocal;
    private String mdescription;

    public Usuario(String name, String local, String description, int photo) {
        mphoto = photo;
        mname = name;
        mlocal = local;
        mdescription = description;
    }

    public int getPhoto() {
        return mphoto;
    }
    public String getName() {
        return mname;
    }
    public String getAge() {
        return mlocal;
    }
    public String getDescription() {
        return mdescription;
    }
}

