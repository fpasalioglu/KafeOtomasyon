package com.example.kafeotomasyon;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kafeotomasyon.adapters.UrunAdapter;
import com.example.kafeotomasyon.models.MenuModel;
import com.example.kafeotomasyon.models.Urun;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.example.kafeotomasyon.GirisEkraniActivity.database;

public class MenuIcerikEkleActivity extends AppCompatActivity {
    private EditText menuadi, birimEdittext, urunadi;
    private Button ekle;
    private ListView listView;
    private TextView empty;
    final List<Urun> urunler = new ArrayList<Urun>();
    private DatabaseReference databaseUrun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuekle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Menü Ekle");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        menuadi = (EditText) findViewById(R.id.menuAdiedittext);
        birimEdittext = (EditText) findViewById(R.id.BirimeditText);
        urunadi = (EditText) findViewById(R.id.UrunAdieditText);
        listView = (ListView) findViewById(R.id.menuurunlistesi);
        ekle = (Button) findViewById(R.id.urunekle);
        empty = (TextView) findViewById(R.id.emptyText);
        databaseUrun = database.child("menuler");

        UrunAdapter adapter = new UrunAdapter(this, urunler);
        listView.setAdapter(adapter);
        listView.setEmptyView(empty);
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urunadi.getText().toString().isEmpty() || birimEdittext.getText().toString().isEmpty()){
                    Toast.makeText(MenuIcerikEkleActivity.this,"Gerekli bölümleri doldurunuz",Toast.LENGTH_LONG).show();
                }else {
                    urunler.add(new Urun(urunadi.getText().toString(), Float.parseFloat(birimEdittext.getText().toString()), 0));
                    adapter.notifyDataSetChanged();
                    birimEdittext.setText("");
                    urunadi.setText("");
                    birimEdittext.requestFocus();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuIcerikEkleActivity.this);
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
        String menuID = menuadi.getText().toString();
        Map<String, Object> postValues = menu.toMap();
        databaseUrun.child(menuID).setValue(postValues);
        finish();
    }
}
