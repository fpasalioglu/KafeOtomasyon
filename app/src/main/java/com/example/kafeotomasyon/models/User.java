package com.example.kafeotomasyon.models;

public class User {
    String uid;
    String isim;
    String gorev;

    public User(){
    }

    public User(String uid, String isim, String gorev) {
        this.uid = uid;
        this.isim = isim;
        this.gorev = gorev;
    }

    public String getIsim(){
        return isim;
    }

    public String getGorev(){
        return gorev;
    }

    public String getUid(){
        return uid;
    }

    public void setIsim(String isim){this.isim = isim;}
    public void setGorev(String gorev){this.gorev=gorev;}
    public void setUid(String uid){this.uid=uid;}
}
