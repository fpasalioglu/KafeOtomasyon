package com.example.kafeotomasyon.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Siparis {
    String siparisadi;
    int adet;
    float birimfiyat;
    float fiyat;

    public Siparis(){
    }

    public Siparis(String siparisadi, int adet, float birimfiyat) {
        this.siparisadi = siparisadi;
        this.adet = adet;
        this.birimfiyat = birimfiyat;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("siparisadi", siparisadi);
        result.put("adet", adet);
        result.put("birimfiyat", birimfiyat);
        result.put("fiyat", fiyat);
        return result;
    }

    public String getSiparisadi(){
        return siparisadi;
    }

    public float getFiyat(){
        return adet*birimfiyat;
    }
}
