package com.example.kafeotomasyon.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuModel {
    String menuadi;
    List<Urun> urunler;

    public MenuModel(){
    }

    public MenuModel(String menuadi, List<Urun> urunler) {
        this.menuadi = menuadi;
        this.urunler = urunler;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("menuadi", menuadi);
        result.put("urunler", urunler);
        return result;
    }

    public String getMenuadi(){
        return menuadi;
    }

    public List<Urun> getUrunler(){
        return urunler;
    }

    public int getUrunSize(){
        if (urunler!=null) {
            return urunler.size();
        }
        return 0;
    }

}
