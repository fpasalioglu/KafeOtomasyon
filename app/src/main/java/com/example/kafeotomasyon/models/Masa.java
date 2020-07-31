package com.example.kafeotomasyon.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Masa {
    String masaadi;
    ArrayList<String> siparisarray;
    float fiyat;

    public Masa(){
    }

    public Masa(String masaadi, ArrayList<String> siparisarray, float fiyat) {
        this.masaadi = masaadi;
        this.siparisarray = siparisarray;
        this.fiyat = fiyat;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("masaadi", masaadi);
        result.put("siparisarray", siparisarray);
        result.put("fiyat", fiyat);
        return result;
    }

    public String getMasaadi(){
        return masaadi;
    }

    public ArrayList<String> getsiparisarray(){
        return siparisarray;
    }
}
