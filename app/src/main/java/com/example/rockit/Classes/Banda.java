package com.example.rockit.Classes;

public class Banda {

    public String id;
    public String imageURL;
    public String name;
    public String age;
    public String email;
    public String year;
    public String description;
    public String members;
    public String stars;
    public String number_followers;
    public String city;
    public String searching_members;
    public String generos;
    public String bandas;
    public String instrumentos;

    public Banda(String id, String name, String age, String email, String year, String description,
                 String members, String stars, String number_followers,
                 String city, String searching_members,
                 String imageURL,
                 String generos, String bandas, String instrumentos) {

        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.age = age;
        this.email = email;
        this.year = year;
        this.description = description;
        this.members = members;
        this.stars = stars;
        this.number_followers = number_followers;
        this.city = city;
        this.searching_members = searching_members;
        this.generos = generos;
        this.bandas = bandas;
        this.instrumentos = instrumentos;
    }

    public Banda(){

    }

    public String getId() {        return id;    }
    public void setId(String id) {        this.id = id;    }

    public String getImageURL() {        return imageURL;    }
    public void setImageURL(String foto) {        this.imageURL = foto;    }

    public void setName(String name) {        this.name = name;    }
    public String getName() {        return name;    }

    public String getAge() {        return age;    }
    public void setAge(String age) {        this.age = age;    }

    public String getYear() {        return year;    }
    public void setYear(String year) {        this.year = year;    }

    public String getSearching_members() {        return searching_members;    }
    public void setSearching_members(String searching_members) {        this.searching_members = searching_members;    }

    public String getEmail() {        return email;    }
    public void setEmail(String email) {        this.email = email;    }

    public String getGeneros() {        return generos;    }
    public void setGeneros(String generos) {        this.generos = generos;    }

    public String getBandas() {        return bandas;    }
    public void setBandas(String bandas) {        this.bandas = bandas;    }

    public String getInstrumentos() {        return instrumentos;    }
    public void setInstrumentos(String instrumentos) {        this.instrumentos = instrumentos;    }

    public String getDescription() {        return description;    }
    public void setDescription(String description) {        this.description = description;    }

    public String getMembers() {        return members;    }
    public void setMembers(String members) {        this.members = members;    }

    public String getStars() {        return stars;    }
    public void setStars(String stars) {        this.stars = stars;    }

    public String getNumber_followers() {        return number_followers;    }
    public void setNumber_followers(String number_followers) {        this.number_followers = number_followers;    }

    public String getCity() {        return city;    }
    public void setCity(String city) {        this.city = city;    }


}

