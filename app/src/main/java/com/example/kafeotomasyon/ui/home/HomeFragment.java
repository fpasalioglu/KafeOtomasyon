package com.example.kafeotomasyon.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kafeotomasyon.MasaYonetimiActivity;
import com.example.kafeotomasyon.R;
import com.example.kafeotomasyon.adapters.MasaAdapter;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    GridView gridView;
    public static List<String> ITEM_LIST;
    public static MasaAdapter imageadapter;
    TextView emptytext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ITEM_LIST = new ArrayList<String>();

        gridView = (GridView) root.findViewById(R.id.gridView1);
        emptytext = (TextView) root.findViewById(R.id.emptyText);

        gridView.setEmptyView( emptytext );

      //  ITEM_LIST.add(ITEM_LIST.size(),"Masa 1");

        imageadapter = new MasaAdapter(getContext(), ITEM_LIST);
        gridView.setAdapter(imageadapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //todo Masa ekranı
                Intent i = new Intent(getContext(), MasaYonetimiActivity.class);
                // Pass image index
                i.putExtra("id", position);
                startActivity(i);
            }
        });

        return root;
    }
}