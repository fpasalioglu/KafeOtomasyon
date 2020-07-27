package com.example.kafeotomasyon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MasaYonetimiActivity extends AppCompatActivity {
    String[] icecekler = {"Listeden Seçiniz", "Çay", "Kahve", "Kola", "Limonata", "Hoşaf"};
    String[] yiyecekler = {"Listeden Seçiniz", "Çorba", "Adana", "Kola", "Limonata", "Hoşaf"};
    ArrayAdapter icecekadapter, yiyecekadapter;
    private ArrayAdapter<String> iceceklistadapter, yiyeceklistadapter;
    private ArrayList<String> icecekarray, yiyecekarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masayonetim_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Masa Yönetimi");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        Spinner icecekspin = (Spinner) findViewById(R.id.iceceklerspinner);
        Spinner yiyecekspin = (Spinner) findViewById(R.id.yiyecekspinner);

        ListView iceceklistView = (ListView) findViewById(R.id.iceceklist);
        ListView yiyeceklistView = (ListView) findViewById(R.id.yiyeceklist);

        icecekarray = new ArrayList<String>();
        yiyecekarray = new ArrayList<String>();

        iceceklistadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, icecekarray);
        yiyeceklistadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, yiyecekarray);
        iceceklistView.setAdapter(iceceklistadapter);
        yiyeceklistView.setAdapter(yiyeceklistadapter);

        icecekadapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, icecekler);
        yiyecekadapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, yiyecekler);

        icecekadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yiyecekadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        icecekspin.setAdapter(icecekadapter);
        yiyecekspin.setAdapter(yiyecekadapter);

        icecekspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Listeden Seçiniz")) {
                    icecekarray.add(parent.getItemAtPosition(position).toString());
                    iceceklistadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yiyecekspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).toString().equals("Listeden Seçiniz")) {
                    yiyecekarray.add(parent.getItemAtPosition(position).toString());
                    yiyeceklistadapter.notifyDataSetChanged();
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
        int id = item.getItemId();
        switch (id){
            /*case R.id.action_search:
                //search iconuna tıklandığında yapılacaklar
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }
}