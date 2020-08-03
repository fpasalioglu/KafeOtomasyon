package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kafeotomasyon.adapters.MenuAdapter;

import static com.example.kafeotomasyon.Utils.Constants.menuicerik;
import static com.example.kafeotomasyon.Utils.Constants.menuisimler;

public class MenuActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private MenuAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Siparis Ekle");
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        listView = findViewById(R.id.lvExp);
        listAdapter = new MenuAdapter(this, menuisimler, menuicerik);
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            listAdapter.siparisler();
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}