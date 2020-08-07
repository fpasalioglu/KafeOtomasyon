package com.example.kafeotomasyon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.kafeotomasyon.adapters.UrunAdapter;
import com.example.kafeotomasyon.models.MenuModel;
import com.example.kafeotomasyon.models.Urun;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.example.kafeotomasyon.GirisEkraniActivity.database;
import static com.example.kafeotomasyon.Utils.Constants.kullanici;
import static com.example.kafeotomasyon.Utils.Constants.menuisimler;

public class MenuIcerikDuzenleActivity extends AppCompatActivity {
    private TextView menuadi;
    private EditText birimEdittext, urunadi;
    private Button ekle;
    private ListView listView;
    private TextView empty;
    private List<Urun> urunler;
    private DatabaseReference databaseMenu;
    private UrunAdapter adapter;
    private CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuduzenle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Menü Düzenle");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        menuadi = (TextView) findViewById(R.id.menuAditext);
        birimEdittext = (EditText) findViewById(R.id.BirimeditText);
        urunadi = (EditText) findViewById(R.id.UrunAdieditText);
        listView = (ListView) findViewById(R.id.menuurunlistesi);
        ekle = (Button) findViewById(R.id.urunekle);
        empty = (TextView) findViewById(R.id.emptyText);
        cardView = (CardView) findViewById(R.id.card2);

        databaseMenu = database.child("menuler");
        FloatingActionButton kapatma =(FloatingActionButton) findViewById(R.id.fab2);
        if (kullanici.getGorev().equals("yonetici")){
            cardView.setVisibility(View.VISIBLE);
            kapatma.setVisibility(View.VISIBLE);
        }

        kapatma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuIcerikDuzenleActivity.this);
                builder.setTitle("Sil?");
                builder.setMessage("Menü Silinsin Mi?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseMenu.child(menuadi.getText().toString()).removeValue();
                        menuisimler.remove(menuadi.getText().toString());
                        finish();
                    }
                });
                builder.show();
            }
        });

        Intent intent=getIntent();
        menuadi.setText(intent.getStringExtra("id"));

        listView.setEmptyView(empty);

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urunler!=null) {
                    urunler.add(new Urun(urunadi.getText().toString(), Float.parseFloat(birimEdittext.getText().toString()),0));
                }else{
                    urunler = new ArrayList<Urun>();
                    urunler.add(new Urun(urunadi.getText().toString(), Float.parseFloat(birimEdittext.getText().toString()),0));
                }
                birimEdittext.setText("");
                urunadi.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuIcerikDuzenleActivity.this);
                builder.setTitle("Sil?");
                builder.setMessage("Ürün Silinsin Mi?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        urunler.remove(arg2);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("menuler").getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    MenuModel menu = snapshot1.getValue(MenuModel.class);

                    if (menu.getMenuadi().equals(menuadi.getText().toString())){
                        urunler = menu.getUrunler();
                    }
                }
                if (urunler == null) {
                    urunler = new ArrayList<Urun>();
                }
                adapter = new UrunAdapter(MenuIcerikDuzenleActivity.this, urunler);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        database.addValueEventListener(postListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                FirebaseSave();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void FirebaseSave(){
        MenuModel menu = new MenuModel(menuadi.getText().toString(), urunler);
        Map<String, Object> postValues = menu.toMap();
        databaseMenu.child(menuadi.getText().toString()).updateChildren(postValues);
        finish();
    }
}

