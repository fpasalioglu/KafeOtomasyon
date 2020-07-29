package com.example.kafeotomasyon;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.kafeotomasyon.models.MenuModel;
import com.example.kafeotomasyon.models.Urun;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import static com.example.kafeotomasyon.Utils.Constants.adetler;
import static com.example.kafeotomasyon.Utils.Constants.menuler;
import static com.example.kafeotomasyon.Utils.Constants.menuler2;
import static com.example.kafeotomasyon.Utils.Constants.sogukicecekler;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static DatabaseReference database;
    final int[] i = {0};
    final int[] urunboyutu = new int[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance().getReference();

        menusayicek();

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

    int menusayisi=0;
    int iceriksayisi=0;
    void menusayicek(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("menuler").getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    MenuModel menu = snapshot1.getValue(MenuModel.class);
                    menusayisi++;
                    if (iceriksayisi<menu.getUrunSize())
                        iceriksayisi=menu.getUrunSize();
                }
                sogukicecekler = new String[menusayisi][iceriksayisi];
                adetler = new int[menusayisi][iceriksayisi];
                menucek();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        database.addValueEventListener(postListener);
    }

    void menucek(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("menuler").getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    MenuModel menu = snapshot1.getValue(MenuModel.class);
                    if (!menuler2.contains(menu.getMenuadi())) {
                        menuler2.add(menu.getMenuadi());
                        List<Urun> urunler = menu.getUrunler();
                        urunboyutu[0] = menu.getUrunSize();
                        for (int c = 0; c<urunboyutu[0]; c++){
                            sogukicecekler[i[0]][c]=urunler.get(c).getUrunadi();
                        }
                        i[0]++;
                    }
                }
                menuler = menuler2.toArray(new String[0]);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        database.addValueEventListener(postListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}