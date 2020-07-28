package com.example.kafeotomasyon.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kafeotomasyon.R;
import com.example.kafeotomasyon.models.Urun;

import java.util.List;

public class UrunAdapter extends BaseAdapter {
    private LayoutInflater userInflater;
    private List<Urun> urunList;

    public UrunAdapter(Activity activity, List<Urun> urunList) {
        userInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.urunList = urunList;
    }

    @Override
    public int getCount() {
        return urunList.size();
    }

    @Override
    public Object getItem(int i) {
        return urunList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = userInflater.inflate(R.layout.urun_listitem, null);
        TextView textViewUrunAdi = (TextView) lineView.findViewById(R.id.title);
        TextView textViewBirimFiyat = (TextView) lineView.findViewById(R.id.textViewbirim);

        Urun urun = urunList.get(i);
        textViewUrunAdi.setText(urun.getUrunadi());
        textViewBirimFiyat.setText(urun.getBirimfiyat());

        return lineView;
    }
}
