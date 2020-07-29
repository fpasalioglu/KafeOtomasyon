package com.example.kafeotomasyon.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kafeotomasyon.MasaEkleActivity;
import com.example.kafeotomasyon.MasaYonetimiActivity;
import com.example.kafeotomasyon.R;
import com.example.kafeotomasyon.adapters.MasaAdapter;
import com.example.kafeotomasyon.models.Masa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.kafeotomasyon.MainActivity.database;

public class HomeFragment extends Fragment {
    GridView gridView;
    public static List<String> Masa_list;
    public static MasaAdapter masaadapter;
    TextView emptytext;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Masa_list = new ArrayList<String>();

        gridView = (GridView) root.findViewById(R.id.gridView1);
        emptytext = (TextView) root.findViewById(R.id.emptyText);
        fab = (FloatingActionButton) root.findViewById(R.id.fab1);

        gridView.setEmptyView( emptytext );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MasaEkleActivity.class);
                startActivity(i);
            }
        });

        masaadapter = new MasaAdapter(getContext(), Masa_list);
        gridView.setAdapter(masaadapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //todo Masa ekranı
                Intent i = new Intent(getContext(), MasaYonetimiActivity.class);
                // Pass image index
                i.putExtra("id", position);
                startActivity(i);
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshot = dataSnapshot.child("masalar").getChildren();
                for (DataSnapshot snapshot1 : snapshot) {
                    Masa masa = snapshot1.getValue(Masa.class);
                    if(!Masa_list.contains(masa.getMasaadi())) {
                        Log.e("ff",masa.getMasaadi());
                        Masa_list.add(Masa_list.size(), masa.getMasaadi());
                        masaadapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        database.addValueEventListener(postListener);

        return root;
    }
}