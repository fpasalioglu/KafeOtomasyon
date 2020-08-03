package com.example.kafeotomasyon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kafeotomasyon.adapters.SiparisAdapter;
import com.example.kafeotomasyon.models.Masa;
import com.example.kafeotomasyon.models.Siparis;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.example.kafeotomasyon.Utils.Constants.REQUEST_CODE;
import static com.example.kafeotomasyon.Utils.Constants.masa_list;
import java.util.ArrayList;
import java.util.Map;

import static com.example.kafeotomasyon.MainActivity.database;
import static com.example.kafeotomasyon.Utils.Constants.siparisarray;

public class MasaYonetimiActivity extends AppCompatActivity {
    private SiparisAdapter listadapter;
    private DatabaseReference databaseMasa;
    private String getmasa;
    float fiyat = 0;
    TextView fiyatText;

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
        databaseMasa = database.child("masalar");

        Intent i = getIntent();
        int id = i.getIntExtra("id",0);
        getmasa = masa_list.get(id);

        ListView siparisListView = (ListView) findViewById(R.id.iceceklist);
        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab1);
        TextView title = (TextView) findViewById(R.id.textView1);
        fiyatText = (TextView) findViewById(R.id.textView3);
        title.setText(getmasa);
        TextView emptytext = (TextView) findViewById(R.id.emptyText);
        siparisListView.setEmptyView(emptytext);

        siparisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasaYonetimiActivity.this);
                builder.setTitle("Sil?");
                builder.setMessage("Ürün Silinsin Mi?");
                builder.setNegativeButton("Hayır", null);
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        siparisarray.remove(arg2);
                        listadapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), MenuActivity.class);
                startActivityForResult(i , REQUEST_CODE);
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("masalar").getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    Masa masa = snapshot1.getValue(Masa.class);
                    if(masa.getMasaadi().equals(getmasa)) {
                        siparisarray = masa.getsiparisarray();
                        if (siparisarray == null) {
                            siparisarray = new ArrayList<Siparis>();
                        }
                        for (int i = 0; i<siparisarray.size(); i++){
                            fiyat += siparisarray.get(i).getFiyat();
                        }
                        fiyatText.setText("Masa Tutarı: "+fiyat+"₺");
                        listadapter = new SiparisAdapter(getApplicationContext(), siparisarray);
                        siparisListView.setAdapter(listadapter);
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
        if (item.getItemId() == R.id.action_save) {
            FirebaseSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void FirebaseSave(){
        Masa masa = new Masa(getmasa, siparisarray);
        Map<String, Object> postValues = masa.toMap();
        databaseMasa.child(getmasa).updateChildren(postValues);
        siparisarray.clear();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
                listadapter.notifyDataSetChanged();
            }
        } catch (Exception ex) {
        }
    }

}