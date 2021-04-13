package com.example.rockit.Classes;

//https://www.youtube.com/watch?v=L5M596DWETg&list=PLxabZQCAe5fio9dm1Vd0peIY6HLfo5MCf&index=8
public class Cards_Tinder {
    private String userID;
    private String name;
    private String instruments;
    private String bands;
    private String imageURL;
    private String genres;
    private String age;
    private String city;
    private String searching_bands;
    private String music_similarity;
    private String distanceKM;
    private String new_match;

    public Cards_Tinder(String userID, String name, String instruments, String bands, String imageURL,
                        String genres, String age, String city,
                        String searching_bands,String music_similarity, String distanceKM, String new_match){
        this.userID = userID;
        this.name = name;
        this.instruments = instruments;
        this.bands = bands;
        this.imageURL = imageURL;
        this.genres=genres;
        this.age = age;
        this.city = city;
        this.searching_bands = searching_bands;
        this.music_similarity = music_similarity;
        this.distanceKM = distanceKM;
        this.new_match = new_match;
    }

    public String getUserID() {        return userID;    }
    public void setUserID(String userID) {        this.userID = userID;    }

    public String getName() {        return name;    }
    public void setName(String name) {        this.name = name;    }

    public String getInstruments() {        return instruments;    }
    public void setInstruments(String instruments) {        this.instruments = instruments;    }

    public String getBands() {        return bands;    }
    public void setBands(String bands) {        this.bands = bands;    }

    public String getImageURL() {        return imageURL;    }
    public void setImageURL(String imageURL) {        this.imageURL = imageURL;    }

    public String getGenres() {        return genres;    }
    public void setGenres(String genres) {        this.genres = genres;    }

    public String getAge() {        return age;    }
    public void setAge(String age) {        this.age = age;    }

    public String getCity() {        return city;    }
    public void setCity(String city) {        this.city = city;    }

    public String getSearching_bands() {        return searching_bands;    }
    public void setSearching_bands(String searching_band) {        this.searching_bands = searching_bands;    }

    public String getMusic_similarity() {        return music_similarity;    }
    public void setMusic_similarity(String music_similarity) {        this.music_similarity = music_similarity;    }

    public String getDistanceKM() {        return distanceKM;    }
    public void setDistanceKM(String distanceKM) {        this.distanceKM = distanceKM;    }

    public String getNewMatch() {        return new_match;    }
    public void setNewMatch(String rejected) {        new_match = new_match;    }
}
