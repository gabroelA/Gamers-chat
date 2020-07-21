package com.example.gamerschat;

public class Contacts {
    public String userName, userId;

    public Contacts() {
    }

    public Contacts(String userName, String imageURL, String uid) {
        this.userName = userName;
        this.userId = uid;
    }

    public String getUid() {
        return userId;
    }

    public void setUid(String uid) {
        this.userId = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
