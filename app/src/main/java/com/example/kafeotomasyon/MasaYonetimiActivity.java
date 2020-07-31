package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kafeotomasyon.models.Masa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import static com.example.kafeotomasyon.Utils.Constants.masa_list;
import java.util.ArrayList;

import static com.example.kafeotomasyon.MainActivity.database;

public class MasaYonetimiActivity extends AppCompatActivity {
    private ArrayAdapter<String> listadapter;
    private ArrayList<String> siparisarray;

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

        Intent i = getIntent();
        int id = i.getIntExtra("id",0);
        String getmasa = masa_list.get(id);

        ListView iceceklistView = (ListView) findViewById(R.id.iceceklist);
        TextView title = (TextView) findViewById(R.id.textView1);
        title.setText(getmasa);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("masalar").getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    Masa masa = snapshot1.getValue(Masa.class);
                    if(masa.getMasaadi().equals(getmasa)) {
                        siparisarray = masa.getsiparisarray();
                        listadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, siparisarray);
                        iceceklistView.setAdapter(listadapter);
                    }
                }
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
            /*case R.id.action_search:
                //search iconuna tıklandığında yapılacaklar
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }
}