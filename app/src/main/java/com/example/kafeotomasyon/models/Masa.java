package com.example.kafeotomasyon.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Masa {
    String masaadi;
    ArrayList<Siparis> siparisarray;

    public Masa(){
    }

    public Masa(String masaadi, ArrayList<Siparis> siparisarray) {
        this.masaadi = masaadi;
        this.siparisarray = siparisarray;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("masaadi", masaadi);
        result.put("siparisarray", siparisarray);
        return result;
    }

    public String getMasaadi(){
        return masaadi;
    }

    public ArrayList<Siparis> getsiparisarray(){
        return siparisarray;
    }
}
