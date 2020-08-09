package com.example.kafeotomasyon.Utils;

import com.example.kafeotomasyon.models.GunlukHasilat;
import com.example.kafeotomasyon.models.Siparis;
import com.example.kafeotomasyon.models.Urun;
import com.example.kafeotomasyon.models.User;
import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
    public static List<String> menuisimler = new ArrayList<String>();
    public static HashMap<String, List<Urun>> menuicerik = new HashMap<>();

    public static List<String> kullanilabirMasalar;
    public static List<String> masa_list;

    public static ArrayList<Siparis> siparisarray = new ArrayList<Siparis>();
    public static ArrayList<GunlukHasilat> gunlukveriler = new ArrayList<GunlukHasilat>();

    public static final int REQUEST_CODE = 1;
    public static User kullanici;
    public static BarData d;
    public static float aylikhasilattoplam;
}
