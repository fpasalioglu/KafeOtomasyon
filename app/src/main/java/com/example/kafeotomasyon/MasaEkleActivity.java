package com.example.kafeotomasyon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kafeotomasyon.adapters.SiparisAdapter;
import com.example.kafeotomasyon.models.Masa;
import com.example.kafeotomasyon.models.Siparis;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Map;
import static com.example.kafeotomasyon.GirisEkraniActivity.database;
import static com.example.kafeotomasyon.Utils.Constants.masa_list;
import static com.example.kafeotomasyon.Utils.Constants.kullanilabirMasalar;
import static com.example.kafeotomasyon.Utils.Constants.siparisarray;

public class MasaEkleActivity extends AppCompatActivity {
    SiparisAdapter siparisadapter;
    String masaadi;
    private DatabaseReference databaseMasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masaekle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Masa Ekle");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        databaseMasa = database.child("masalar");

        Spinner masaspinner = (Spinner) findViewById(R.id.spinner);
        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab1);
        ListView siparislist = (ListView) findViewById(R.id.listsiparis);
        TextView emptytext = (TextView) findViewById(R.id.emptyText);
        siparisarray.clear();
        siparislist.setEmptyView(emptytext);
        siparisadapter = new SiparisAdapter(getApplicationContext(), siparisarray);
        siparislist.setAdapter(siparisadapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), MenuActivity.class);
                startActivity(i);
            }
        });

        for (int i=0;i<masa_list.size();i++){
            kullanilabirMasalar.remove(masa_list.get(i));
        }

        ArrayAdapter<String> masaadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, kullanilabirMasalar);
        masaspinner.setAdapter(masaadapter);
        masaspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Masa numarası seçiniz")) {
                    masaadi = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            FirebaseSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void FirebaseSave(){
        Masa masa = new Masa(masaadi, siparisarray);
        Map<String, Object> postValues = masa.toMap();
        databaseMasa.child(masaadi).setValue(postValues);
        siparisarray.clear();
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        siparisadapter.notifyDataSetChanged();
    }
}
