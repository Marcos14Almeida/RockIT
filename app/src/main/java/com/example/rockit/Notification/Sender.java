package com.example.rockit.Notification;

//https://www.youtube.com/watch?v=7Xc_5cduL-Y 7:15
public class Sender {
    public Data data;
    public String to; //user token to pass notification

    public Sender(Data data, String to) {
        this.data = data;
        this.to = to;
    }

    public Sender(){}

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
