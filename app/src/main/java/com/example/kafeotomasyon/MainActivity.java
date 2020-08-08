package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kafeotomasyon.models.AylikHasilat;
import com.example.kafeotomasyon.models.GunlukHasilat;
import com.example.kafeotomasyon.models.MenuModel;
import com.example.kafeotomasyon.models.User;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.kafeotomasyon.GirisEkraniActivity.database;
import static com.example.kafeotomasyon.Utils.Constants.kasiyerarray;
import static com.example.kafeotomasyon.Utils.Constants.gunlukveriler;
import static com.example.kafeotomasyon.Utils.Constants.menuicerik;
import static com.example.kafeotomasyon.Utils.Constants.menuisimler;
import static com.example.kafeotomasyon.Utils.Constants.kullanici;
import static com.example.kafeotomasyon.Utils.Constants.d;
import static com.example.kafeotomasyon.Utils.Constants.d2;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private ArrayList<AylikHasilat> aylikveriler = new ArrayList<>();

    private DatabaseReference databaseAylik, databaseGunluk;
    private int dayOfMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar cal = Calendar.getInstance();
        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        databaseAylik = database.child("aylikhasilat");
        databaseGunluk = database.child("gunlukhasilat").child(String.valueOf(dayOfMonth));

        mAuth = FirebaseAuth.getInstance();
        kullaniciverisi();
        menucek();
        generateBarData();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void navTextChange(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        String cap = kullanici.getGorev().substring(0, 1).toUpperCase() + kullanici.getGorev().substring(1);
        navUsername.setText(cap +": "+kullanici.getIsim());
    }

    void menucek(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("menuler").getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    MenuModel menu = snapshot1.getValue(MenuModel.class);
                    if (!menuisimler.contains(menu.getMenuadi())) {
                        menuisimler.add(menu.getMenuadi());
                        menuicerik.put(menu.getMenuadi(), menu.getUrunler());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        database.addValueEventListener(postListener);
    }

    void kullaniciverisi(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("users").getChildren();
                kasiyerarray.clear();
                for (DataSnapshot snapshot1 : snapshot) {
                    User user = snapshot1.getValue(User.class);
                    if (mAuth.getCurrentUser().getUid().equals(user.getUid())) {
                        kullanici = new User(user.getUid(), user.getIsim(), user.getGorev());
                    }
                    if (user.getGorev().equals("kasiyer")){
                        kasiyerarray.add(user);
                    }
                }
                navTextChange();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        database.addValueEventListener(postListener);
    }

    public void generateBarData() {
        ValueEventListener eventListener7 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    AylikHasilat aylikHasilat = snapshot1.getValue(AylikHasilat.class);
                    aylikveriler.add(aylikHasilat);
                }

                ArrayList<IBarDataSet> sets = new ArrayList<>();
                ArrayList<BarEntry> entries = new ArrayList<>();

                for(int j = 0; j < aylikveriler.size(); j++) {
                    entries.add(new BarEntry(Integer.parseInt(aylikveriler.get(j).getGun()), aylikveriler.get(j).getHasilat()));
                }

                BarDataSet ds = new BarDataSet(entries, "Aylık Hasılat");
                ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
                ds.setValueTextSize(20f);
                sets.add(ds);

                d = new BarData(sets);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseAylik.addListenerForSingleValueEvent(eventListener7);

        ValueEventListener eventListener8 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.getChildren();
                gunlukveriler.clear();
                for (DataSnapshot snapshot1 : snapshot) {
                    GunlukHasilat gunlukHasilat = snapshot1.getValue(GunlukHasilat.class);
                    gunlukveriler.add(gunlukHasilat);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseGunluk.addListenerForSingleValueEvent(eventListener8);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}