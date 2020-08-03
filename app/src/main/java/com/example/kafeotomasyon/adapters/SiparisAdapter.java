package com.example.kafeotomasyon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kafeotomasyon.R;
import com.example.kafeotomasyon.models.Siparis;

import java.util.ArrayList;

public class SiparisAdapter extends BaseAdapter {

    private ArrayList<Siparis> siparisler;
    private Context context;
    private LayoutInflater layoutInflater;

    public SiparisAdapter(Context context, ArrayList<Siparis> siparisler){
        this.context = context;
        this.siparisler = siparisler;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return siparisler.size();
    }

    @Override
    public Object getItem(int i) {
        return siparisler.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View customView = layoutInflater.inflate(R.layout.item_siparis,null);
        TextView name = (TextView) customView.findViewById(R.id.title);
        TextView adet = (TextView) customView.findViewById(R.id.adetText);
        name.setText(siparisler.get(i).getSiparisadi());
        adet.setText(String.valueOf(siparisler.get(i).getAdet()));
        return customView;
    }
}
