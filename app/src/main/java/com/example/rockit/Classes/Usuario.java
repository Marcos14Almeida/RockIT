package com.example.rockit.Classes;

public class Usuario {

    public String id;
    public String imageURL;
    public String name;
    public String age;
    public String email;
    public String sex;
    public String description;
    public String status;
    public String stars;
    public String first_login;
    public String latitude;
    public String longitude;
    public String searching_bands;
    public String generos;
    public String bandas;
    public String instrumentos;
    public String contact;
    public String last_seen;
    public String instagram;

    public Usuario(String id, String name, String age, String email, String sex, String description,
                   String status, String stars, String first_login,
                   String latitude, String longitude, String searching_bands,
                   String imageURL,
                   String generos, String bandas, String instrumentos,
                    String contact, String last_seen, String instagram) {

        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.age = age;
        this.email = email;
        this.sex = sex;
        this.description = description;
        this.status = status;
        this.stars = stars;
        this.first_login = first_login;
        this.latitude = latitude;
        this.longitude = longitude;
        this.searching_bands = searching_bands;
        this.generos = generos;
        this.bandas = bandas;
        this.instrumentos = instrumentos;
        this.contact = contact;
        this.last_seen = last_seen;
        this.instagram = instagram;
    }

    public Usuario(){

    }

    public String getId() {        return id;    }
    public void setId(String id) {        this.id = id;    }

    public String getImageURL() {        return imageURL;    }
    public void setImageURL(String foto) {        this.imageURL = foto;    }

    public void setName(String name) {        this.name = name;    }
    public String getName() {        return name;    }

    public String getAge() {        return age;    }
    public void setAge(String age) {        this.age = age;    }

    public String getEmail() {        return email;    }
    public void setEmail(String email) {        this.email = email;    }

    public String getGeneros() {        return generos;    }
    public void setGeneros(String generos) {        this.generos = generos;    }

    public String getBandas() {        return bandas;    }
    public void setBandas(String bandas) {        this.bandas = bandas;    }

    public String getInstrumentos() {        return instrumentos;    }
    public void setInstrumentos(String instrumentos) {        this.instrumentos = instrumentos;    }

    public String getSex() {        return sex;    }
    public void setSex(String sex) {        this.sex = sex;    }

    public String getDescription() {        return description;    }
    public void setDescription(String description) {        this.description = description;    }

    public String getStatus() {        return status;    }
    public void setStatus(String status) {        this.status = status;    }

    public String getStars() {        return stars;    }
    public void setStars(String stars) {        this.stars = stars;    }

    public String getFirst_login() {        return first_login;    }
    public void setFirst_login(String first_login) {        this.first_login = first_login;    }

    public String getLatitude() {        return latitude;    }
    public void setLatitude(String latitude) {        this.latitude = latitude;    }

    public String getLongitude() {        return longitude;    }
    public void setLongitude(String longitude) {        this.longitude = longitude;    }

    public String getSearching_bands() {        return searching_bands;    }
    public void setSearching_bands(String searching_bands) {        this.searching_bands = searching_bands;    }

    public String getContact() {        return contact;    }
    public void setContact(String contact) {        this.contact = contact;    }

    public String getLast_seen() {        return last_seen;    }
    public void setLast_seen(String last_seen) {        this.last_seen = last_seen;    }

    public String getInstagram() {        return instagram;    }
    public void setInstagram(String instagram) {        this.instagram = instagram;    }
}

