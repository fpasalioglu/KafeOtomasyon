package com.example.kafeotomasyon.ui.hasilat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kafeotomasyon.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;

import static com.example.kafeotomasyon.Utils.Constants.d;
import static com.example.kafeotomasyon.Utils.Constants.gunlukveriler;
import static com.example.kafeotomasyon.Utils.Constants.kasiyerarray;

public class HasilatFragment extends Fragment {

    float barWidth;
    float barSpace;
    float groupSpace;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_malirapor, container, false);

        BarChart chart = root.findViewById(R.id.chart);

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.animateY(1000);
        chart.setData(d);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;

        BarChart chart2 = (BarChart) root.findViewById(R.id.chart2);
        chart2.getDescription().setEnabled(false);
        chart2.setDrawGridBackground(false);
        chart2.setDrawBarShadow(false);
        chart2.setScaleEnabled(false);
        chart2.animateY(1000);

        int groupCount = kasiyerarray.size();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        ArrayList<String> xVals = new ArrayList<String>();
            for (int i = 0; i < gunlukveriler.size(); i++) {
                xVals.add(kasiyerarray.get(i).getIsim());
                yVals1.add(new BarEntry(i + 1, (float) gunlukveriler.get(i).getNakithasilat()));
                yVals2.add(new BarEntry(i + 1, (float) gunlukveriler.get(i).getKredihasilat()));
            }


        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Nakit");
        set1.setColor(Color.RED);
        set1.setValueTextSize(16f);
        set2 = new BarDataSet(yVals2, "Kredi");
        set2.setColor(Color.BLUE);
        set2.setValueTextSize(16f);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());

        if (gunlukveriler.size()!=0) {
            chart2.setData(data);
            chart2.getBarData().setBarWidth(barWidth);
            chart2.getXAxis().setAxisMinimum(0);
            chart2.getXAxis().setAxisMaximum(0 + chart2.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
            chart2.groupBars(0, groupSpace, barSpace);
            chart2.getData().setHighlightEnabled(false);
            chart2.invalidate();
        }
        chart2.setNoDataText("Veri Bulunamadı");

        Legend l = chart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(14f);

        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setGranularity(1f);
        xAxis2.setTextSize(14f);
        xAxis2.setGranularityEnabled(true);
        xAxis2.setCenterAxisLabels(true);
        xAxis2.setDrawGridLines(false);
        xAxis2.setAxisMaximum(groupCount);
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setValueFormatter(new IndexAxisValueFormatter(xVals));

        chart2.getAxisRight().setEnabled(false);
        YAxis leftAxis2 = chart2.getAxisLeft();
        leftAxis2.setValueFormatter(new LargeValueFormatter());
        leftAxis2.setDrawGridLines(false);
        leftAxis2.setSpaceTop(35f);
        leftAxis2.setAxisMinimum(0f);

        return root;
    }
}