package com.example.kafeotomasyon.models;

public class Urun {
    private String urunadi;
    private String birimfiyat;

    public Urun(){
    }

    public Urun(String urunadi, String birimfiyat) {
        this.urunadi = urunadi;
        this.birimfiyat = birimfiyat;
    }

    public String getUrunadi() {
        return urunadi;
    }

    public String getBirimfiyat() {
        return birimfiyat;
    }

}
