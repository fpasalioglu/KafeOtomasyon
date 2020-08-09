package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kafeotomasyon.models.Masa;
import com.example.kafeotomasyon.models.Siparis;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.kafeotomasyon.GirisEkraniActivity.database;
import static com.example.kafeotomasyon.Utils.Constants.masa_list;

public class BirlestirActivity extends AppCompatActivity {
    String tasinacakMasa="", hedefMasa="";
    ArrayList<Siparis> tasinacaksiparis = new ArrayList<Siparis>();
    ArrayList<Siparis> hedefsiparis = new ArrayList<Siparis>();
    float tasinacakfiyat, hedeffiyat;
    DatabaseReference databaseMasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birlestir);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Masa Birleştir");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        Button birlestir = (Button) findViewById(R.id.birlestirbutton);
        Spinner tasinanmasaspinner = (Spinner) findViewById(R.id.spinner2);
        Spinner hedefmasaspinner = (Spinner) findViewById(R.id.spinner3);

        databaseMasa = database.child("masalar");

        List<String> yenimasa_list = new ArrayList<String>();
        yenimasa_list.add("Masa numarası seçiniz");
        yenimasa_list.addAll(masa_list);

        ArrayAdapter<String> tasinanmasaadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, yenimasa_list);
        tasinanmasaspinner.setAdapter(tasinanmasaadapter);
        tasinanmasaspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Masa numarası seçiniz")) {
                    tasinacakMasa = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> hedefmasaadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, yenimasa_list);
        hedefmasaspinner.setAdapter(hedefmasaadapter);
        hedefmasaspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Masa numarası seçiniz")) {
                    hedefMasa = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        birlestir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();
                        for (DataSnapshot snapshot1 : snapshot) {
                            Masa masa = snapshot1.getValue(Masa.class);
                            if(masa.getMasaadi().equals(tasinacakMasa)) {
                                tasinacaksiparis = masa.getsiparisarray();
                                if (tasinacaksiparis == null) {
                                    tasinacaksiparis = new ArrayList<Siparis>();
                                }
                                for (int i = 0; i<tasinacaksiparis.size(); i++){
                                    tasinacakfiyat += tasinacaksiparis.get(i).getFiyat();
                                }
                            }
                            if(masa.getMasaadi().equals(hedefMasa)) {
                                hedefsiparis = masa.getsiparisarray();
                                if (hedefsiparis == null) {
                                    hedefsiparis = new ArrayList<Siparis>();
                                }
                                for (int i = 0; i<hedefsiparis.size(); i++){
                                    hedeffiyat += hedefsiparis.get(i).getFiyat();
                                }
                            }
                        }

                        hedefsiparis.addAll(tasinacaksiparis);

                        Masa masa = new Masa(hedefMasa, hedefsiparis);
                        Map<String, Object> postValues = masa.toMap();
                        databaseMasa.child(hedefMasa).setValue(postValues);
                        databaseMasa.child(tasinacakMasa).removeValue();
                        masa_list.remove(tasinacakMasa);
                        finish();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                databaseMasa.addListenerForSingleValueEvent(postListener);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent i = new Intent(BirlestirActivity.this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
