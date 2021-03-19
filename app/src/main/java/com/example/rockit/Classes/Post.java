package com.example.rockit.Classes;

//INSTAGRAM App with Firebase
//https://www.youtube.com/watch?v=mk2CrU-awkM&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=8
public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String publisher;
    private String timestamp;

    public Post(String postid, String postimage,String description, String publisher, String timestamp){
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
        this.timestamp = timestamp;
    }

    public Post(){}

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTimestamp() {        return timestamp;    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
