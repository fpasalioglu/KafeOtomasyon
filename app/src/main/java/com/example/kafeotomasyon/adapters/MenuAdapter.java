package com.example.kafeotomasyon.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.kafeotomasyon.R;
import com.example.kafeotomasyon.models.Siparis;
import com.example.kafeotomasyon.models.Urun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.kafeotomasyon.Utils.Constants.siparisarray;

public class MenuAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<Urun>> listHashMap;

    public MenuAdapter(Context context, List<String> listDataHeader, HashMap<String, List<Urun>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null){

            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Urun urun = (Urun) getChild(groupPosition, childPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.textviewmenu);
        final TextView txt = convertView.findViewById(R.id.textView);
        final Button button = convertView.findViewById(R.id.button_arttir);
        final Button button2 = convertView.findViewById(R.id.button_azalt);
        txt.setText(String.valueOf(urun.getAdet()));

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                urun.adetArttir();
                txt.setText(String.valueOf(urun.getAdet()));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                urun.adetAzalt();
                txt.setText(String.valueOf(urun.getAdet()));
            }
        });

        txtListChild.setText(urun.getUrunadi());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public ArrayList<Siparis> siparisler(){
        for (int i = 0; i<getGroupCount(); i++){
            for (int j = 0; j<getChildrenCount(i); j++){
                Urun urun = (Urun) getChild(i, j);
                if (urun.getAdet() > 0){
                    for (int c = 0;c<siparisarray.size();c++){
                        if (siparisarray.get(c).getSiparisadi()==urun.getUrunadi())
                            siparisarray.remove(c);
                    }
                    siparisarray.add(new Siparis(urun.getUrunadi(),urun.getAdet(),urun.getBirimfiyat()*urun.getAdet()));
                    urun.setAdet(0);
                }
            }
        }
        return siparisarray;
    }
}
