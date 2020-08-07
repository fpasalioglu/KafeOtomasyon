package com.example.kafeotomasyon;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
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
import static com.example.kafeotomasyon.Utils.Constants.kullanici;
import static com.example.kafeotomasyon.Utils.Constants.masa_list;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static com.example.kafeotomasyon.GirisEkraniActivity.database;
import static com.example.kafeotomasyon.Utils.Constants.siparisarray;

public class MasaYonetimiActivity extends AppCompatActivity {
    private SiparisAdapter listadapter;
    private DatabaseReference databaseMasa, databaseKasa, databaseNakit, databaseKredi, databaseAylik;
    private String getmasa;
    private float fiyat = 0;
    private TextView fiyatText;
    private Dialog myDialog;
    private float eski, eskiNakit, eskiKredi, eskiAylik;
    int dayOfMonth;
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

        Calendar cal = Calendar.getInstance();
        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        databaseMasa = database.child("masalar");
        databaseKasa = database.child("gunlukhasilat").child(kullanici.getIsim()).child(String.valueOf(dayOfMonth)).child("toplam");
        databaseNakit = database.child("gunlukhasilat").child(kullanici.getIsim()).child(String.valueOf(dayOfMonth)).child("nakit");
        databaseKredi = database.child("gunlukhasilat").child(kullanici.getIsim()).child(String.valueOf(dayOfMonth)).child("kredi");
        databaseAylik = database.child("aylikhasilat").child(String.valueOf(dayOfMonth));

        myDialog = new Dialog(this);
        Intent i = getIntent();
        int id = i.getIntExtra("id",0);
        getmasa = masa_list.get(id);

        ListView siparisListView = (ListView) findViewById(R.id.iceceklist);
        FloatingActionButton fab =(FloatingActionButton) findViewById(R.id.fab1);
        FloatingActionButton kapatma =(FloatingActionButton) findViewById(R.id.fab2);
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

        if (kullanici.getGorev().equals("kasiyer") || kullanici.getGorev().equals("yonetici")){
            kapatma.setVisibility(View.VISIBLE);
        }

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

    boolean kredi = false;
    public void ShowPopup(View v) {
        TextView txtclose, masaText, tutar;
        Button btnKapat;
        RadioGroup radioGroup;

        myDialog.setContentView(R.layout.masakapat_popup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        masaText =(TextView) myDialog.findViewById(R.id.masaname);
        tutar =(TextView) myDialog.findViewById(R.id.toplamtutar);
        btnKapat = (Button) myDialog.findViewById(R.id.btnKapat);
        radioGroup = (RadioGroup) myDialog.findViewById(R.id.radio);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                kredi = checkedId == R.id.radioButton5;
            }
        });

        tutar.setText(fiyat+"₺");
        masaText.setText(getmasa);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value="";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    value = ds.getValue().toString();
                }
                if (!value.equals(""))
                eski = Float.parseFloat(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseKasa.addListenerForSingleValueEvent(eventListener);

        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value="";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    value = ds.getValue().toString();
                }
                if (!value.equals(""))
                    eskiNakit = Float.parseFloat(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseNakit.addListenerForSingleValueEvent(eventListener2);

        ValueEventListener eventListener3 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value="";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    value = ds.getValue().toString();
                }
                if (!value.equals(""))
                    eskiKredi = Float.parseFloat(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseKredi.addListenerForSingleValueEvent(eventListener3);

        ValueEventListener eventListener4 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value="";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    value = ds.getValue().toString();
                }
                if (!value.equals(""))
                    eskiAylik = Float.parseFloat(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseAylik.addListenerForSingleValueEvent(eventListener4);

        btnKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("hasilat", eski + fiyat);
                databaseKasa.setValue(result);

                HashMap<String, Object> result4 = new HashMap<>();
                result4.put("gun", String.valueOf(dayOfMonth));
                result4.put("hasilat", eskiAylik + fiyat);
                databaseAylik.setValue(result4);

                if (kredi){
                    HashMap<String, Object> result2 = new HashMap<>();
                    result2.put("hasilat", eskiKredi + fiyat);
                    databaseKredi.setValue(result2);
                }else {
                    HashMap<String, Object> result3 = new HashMap<>();
                    result3.put("hasilat", eskiNakit + fiyat);
                    databaseNakit.setValue(result3);
                }

                databaseMasa.child(getmasa).removeValue();
                myDialog.dismiss();
                finish();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
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