package com.example.kafeotomasyon.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Masa {
    String masaadi;
    ArrayList<String> icecekarray, yiyecekarray;
    int fiyat;

    public Masa(){
    }

    public Masa(String masaadi, ArrayList<String> icecekarray, ArrayList<String> yiyecekarray, int fiyat) {
        this.masaadi = masaadi;
        this.icecekarray = icecekarray;
        this.yiyecekarray = yiyecekarray;
        this.fiyat = fiyat;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("masaadi", masaadi);
        result.put("icecekler", icecekarray);
        result.put("yiyecekler", yiyecekarray);
        result.put("fiyat", fiyat);
        return result;
    }

    public String getMasaadi(){
        return masaadi;
    }
}
