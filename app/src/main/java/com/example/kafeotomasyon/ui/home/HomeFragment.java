package com.example.kafeotomasyon.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kafeotomasyon.BirlestirActivity;
import com.example.kafeotomasyon.MasaEkleActivity;
import com.example.kafeotomasyon.MasaYonetimiActivity;
import com.example.kafeotomasyon.R;
import com.example.kafeotomasyon.SettingsActivity;
import com.example.kafeotomasyon.adapters.MasaAdapter;
import com.example.kafeotomasyon.models.Masa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import static com.example.kafeotomasyon.Utils.Constants.masa_list;
import static com.example.kafeotomasyon.GirisEkraniActivity.database;
import static com.example.kafeotomasyon.Utils.Constants.kullanilabirMasalar;

public class HomeFragment extends Fragment {
    GridView gridView;

    public static MasaAdapter masaadapter;
    TextView emptytext;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        masa_list = new ArrayList<String>();
        kullanilabirMasalar = new ArrayList<String>();

        kullanilabirMasalar.add("Masa numarası seçiniz");
        for (int i=1;i<=15;i++){
            kullanilabirMasalar.add("Masa "+i);
        }

        gridView = (GridView) root.findViewById(R.id.gridView1);
        emptytext = (TextView) root.findViewById(R.id.emptyText);
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab1);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        gridView.setEmptyView( emptytext );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MasaEkleActivity.class);
                startActivity(i);
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getContext(), MasaYonetimiActivity.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("masalar").getChildren();
                masa_list.clear();
                for (DataSnapshot snapshot1 : snapshot) {
                    Masa masa = snapshot1.getValue(Masa.class);
                    if(!masa_list.contains(masa.getMasaadi())) {
                        masa_list.add(masa.getMasaadi());
                    }
                }
                masaadapter = new MasaAdapter(getContext(), masa_list);
                gridView.setAdapter(masaadapter);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        database.addValueEventListener(postListener);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.homefragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent i = new Intent(getActivity(), SettingsActivity.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.action_birlestir){
            Intent i = new Intent(getActivity(), BirlestirActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}