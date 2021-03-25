package com.example.rockit.Classes;

public class Usuario_basic {

    public String id;
    public String imageURL;
    public String name;
    public String email;

    public Usuario_basic(String id, String imageURL, String name, String email){
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.email = email;
    }

    public Usuario_basic(){
    }

    public String getId() {        return id;    }
    public void setId(String id) {        this.id = id;    }

    public String getImageURL() {        return imageURL;    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {        return name;    }
    public void setName(String name) {        this.name = name;    }

    public String getEmail() {        return email;    }
    public void setEmail(String email) {        this.email = email;    }
}
