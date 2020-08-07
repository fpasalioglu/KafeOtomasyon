package com.example.kafeotomasyon.models;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class AylikHasilat {
    String gun;
    float hasilat;

    public AylikHasilat(){
    }

    public AylikHasilat(String gun, float hasilat) {
        this.gun = gun;
        this.hasilat = hasilat;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("gun", gun);
        result.put("hasilat", hasilat);
        return result;
    }

    public String getGun(){
        return gun;
    }
    public float getHasilat(){
        return hasilat;
    }
}
