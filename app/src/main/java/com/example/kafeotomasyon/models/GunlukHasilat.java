package com.example.kafeotomasyon.models;

public class GunlukHasilat {
    float nakithasilat;
    float kredihasilat;
    float toplamhasilat;

    public GunlukHasilat(){
    }

    public GunlukHasilat(float nakithasilat, float kredihasilat, float toplamhasilat) {
        this.nakithasilat = nakithasilat;
        this.kredihasilat = kredihasilat;
        this.toplamhasilat = toplamhasilat;
    }

    public float getNakithasilat(){
        return nakithasilat;
    }
    public float getKredihasilat(){
        return kredihasilat;
    }
    public float getToplamhasilat(){
        return toplamhasilat;
    }
}
