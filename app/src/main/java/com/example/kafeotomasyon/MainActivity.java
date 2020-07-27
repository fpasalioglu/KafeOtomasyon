package com.example.kafeotomasyon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.kafeotomasyon.common.MenuActivity;
import com.example.kafeotomasyon.models.Masa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import static com.example.kafeotomasyon.ui.home.HomeFragment.ITEM_LIST;
import static com.example.kafeotomasyon.ui.home.HomeFragment.imageadapter;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
int a =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseMasa = database.child("masalar");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  a++;


                ArrayList<String> icecekarray = new ArrayList<String>();
                ArrayList<String> yiyecekarray = new ArrayList<String>();
                icecekarray.add("ayran");
                yiyecekarray.add("corba");
                Masa masa = new Masa("masa "+a,icecekarray,yiyecekarray,25);

                Map<String, Object> postValues = masa.toMap();
                databaseMasa.push().setValue(postValues);*/

                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);

            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("masalar").getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    Masa masa = snapshot1.getValue(Masa.class);
                    if(!ITEM_LIST.contains(masa.getMasaadi())) {
                        ITEM_LIST.add(ITEM_LIST.size(), masa.getMasaadi());
                        imageadapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        database.addValueEventListener(postListener);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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