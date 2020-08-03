package com.example.kafeotomasyon.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Siparis implements java.io.Serializable{
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("siparisadi", siparisadi);
        result.put("adet", adet);
        result.put("fiyat", fiyat);
        return result;
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
