package com.example.kafeotomasyon.ui.menuekle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kafeotomasyon.MenuIcerikDuzenleActivity;
import com.example.kafeotomasyon.MenuIcerikEkleActivity;
import com.example.kafeotomasyon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static com.example.kafeotomasyon.Utils.Constants.menuler;

public class MenuEkleFragment extends Fragment {
    ListView listItemView;
    FloatingActionButton fab;
    ArrayAdapter<String> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menuler, container, false);

        listItemView = (ListView) root.findViewById(R.id.listView1);
        fab = (FloatingActionButton) root.findViewById(R.id.fab1);
         adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, menuler);

        listItemView.setAdapter(adapter);
        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getContext(), MenuIcerikDuzenleActivity.class);
                intent.putExtra("id", menuler[arg2]);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MenuIcerikEkleActivity.class);
                startActivity(i);
            }
        });
        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}