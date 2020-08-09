package com.example.kafeotomasyon.models;

public class GunlukHasilat {
    private String kasiyeradi;
    float nakithasilat;
    float kredihasilat;
    float toplamhasilat;

    public GunlukHasilat(){
    }

    public GunlukHasilat(String kasiyeradi, float nakithasilat, float kredihasilat, float toplamhasilat) {
        this.kasiyeradi = kasiyeradi;
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
    public String getKasiyeradi(){
        return kasiyeradi;
    }
    public float getToplamhasilat(){
        return toplamhasilat;
    }
}
