package com.example.kafeotomasyon.models;

public class AylikHasilat {
    String gun;
    float hasilat;

    public AylikHasilat(){
    }

    public AylikHasilat(String gun, float hasilat) {
        this.gun = gun;
        this.hasilat = hasilat;
    }

    public String getGun(){
        return gun;
    }
    public float getHasilat(){
        return hasilat;
    }
}
