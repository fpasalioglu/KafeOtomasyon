package com.example.kafeotomasyon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kafeotomasyon.R;

import java.util.List;

public class MasaAdapter extends BaseAdapter {
    private Context context;
    private final List<String> list;

    public MasaAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.grid_item, null);

            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(list.get(position));

            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
            imageView.setImageResource(R.drawable.table);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
