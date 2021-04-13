package com.example.rockit.Classes;

public class Chat {

    private String sender;
    private String receiver;
    private String message;
    private String timestamp;
    private Boolean isseen;

    public Chat(String sender, String receiver, String message, String timestamp, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
        this.isseen = isseen;
    }

    public Chat(){

    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {        return timestamp;    }

    public void setTimestamp(String timestamp) {        this.timestamp = timestamp;    }

    public Boolean getIsseen() {        return isseen;    }
    public void setIsseen(Boolean isseen) {        this.isseen = isseen;    }


}
