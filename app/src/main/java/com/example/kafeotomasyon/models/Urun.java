package com.example.kafeotomasyon.models;

public class Urun {
    private String urunadi;
    private float birimfiyat;
    private int adet;

    public Urun(){
    }

    public Urun(String urunadi, float birimfiyat, int adet) {
        this.urunadi = urunadi;
        this.birimfiyat = birimfiyat;
        this.adet = adet;
    }

    public String getUrunadi() {
        return urunadi;
    }

    public void setUrunadi(String urunadi) {
        this.urunadi = urunadi;
    }

    public float getBirimfiyat() {
        return birimfiyat;
    }

    public void setBirimfiyat(float birimfiyat) {
        this.birimfiyat = birimfiyat;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public int getAdet() {
        return adet;
    }

    public void adetArttir() {
        this.adet ++;
    }

    public void adetAzalt() {
        if (this.adet!=0)
            this.adet --;
    }
}
