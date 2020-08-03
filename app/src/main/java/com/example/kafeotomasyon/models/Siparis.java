package com.example.kafeotomasyon.models;

public class Siparis {
    String siparisadi;
    int adet;
    float fiyat;

    public Siparis(){
    }

    public Siparis(String siparisadi, int adet, float fiyat) {
        this.siparisadi = siparisadi;
        this.adet = adet;
        this.fiyat = fiyat;
    }

    public String getSiparisadi(){
        return siparisadi;
    }

    public float getFiyat(){
        return fiyat;
    }

    public int getAdet(){
        return adet;
    }
}
