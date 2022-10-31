package org.infinite.mantra.ui.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.infinite.mantra.R;
import org.infinite.mantra.database.dao.PetographDAO;
import org.infinite.mantra.database.model.PetographModel;

import java.util.ArrayList;
import java.util.List;

public class ChartsFragment extends Fragment {
    LineChart lineChart;
    List<PetographModel> dbList;
    PetographDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);

        initViews(view);
        getChartData();

        return view;
    }

    private void initViews(@NonNull View view) {
        lineChart = view.findViewById(R.id.lineChart);
        dao = new PetographDAO(getContext());
    }

    private void getChartData() {
        ArrayList<Entry> systolicBP = new ArrayList<>();
        ArrayList<Entry> diastolicBP = new ArrayList<>();
        ArrayList<Entry> pulseRate = new ArrayList<>();
        dbList = dao.getDataFromDB();

        if (dbList.size() != 0) {
            for (int i = 0; i < dbList.size(); i++) {
                int systolic = Integer.parseInt(dbList.get(i).getSystolic());
                int diastolic = Integer.parseInt(dbList.get(i).getDiastolic());
                int pulse = Integer.parseInt(dbList.get(i).getPulseRate());
                systolicBP.add(new Entry(i, systolic));
                diastolicBP.add(new Entry(i, diastolic));
                pulseRate.add(new Entry(i, pulse));
            }
        }
        setChartData(systolicBP, diastolicBP, pulseRate);
    }

    private void setChartData(ArrayList<Entry> data, ArrayList<Entry> data1, ArrayList<Entry> data2) {
        LineDataSet systolicDataSet = new LineDataSet(data, "Systolic BP");
        LineDataSet diastolicDataSet = new LineDataSet(data1, "Diastolic BP");
        LineDataSet pulseDataSet = new LineDataSet(data2, "Pulse Rate");

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();

        iLineDataSets.add(systolicDataSet);
        iLineDataSets.add(diastolicDataSet);
        iLineDataSets.add(pulseDataSet);

        // SET CHART KEY COLORS
        ((LineDataSet) iLineDataSets.get(0)).setColors(Color.BLUE);
        ((LineDataSet) iLineDataSets.get(1)).setColors(Color.GREEN);
        ((LineDataSet) iLineDataSets.get(2)).setColors(Color.RED);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setNoDataText("Add vitals in check up screen to see graph data here");
        lineChart.setData(lineData);
        lineChart.invalidate();

        // Chart styling
        systolicDataSet.setColor(Color.BLUE);
        systolicDataSet.setDrawCircles(true);
        systolicDataSet.setCircleColor(Color.BLUE);
        systolicDataSet.setDrawCircleHole(true);
        systolicDataSet.setLineWidth(2);
        systolicDataSet.setCircleRadius(5);
        systolicDataSet.setValueTextSize(10);

        diastolicDataSet.setColor(Color.GREEN);
        diastolicDataSet.setDrawCircles(true);
        diastolicDataSet.setCircleColor(Color.GREEN);
        diastolicDataSet.setDrawCircleHole(true);
        diastolicDataSet.setLineWidth(2);
        diastolicDataSet.setCircleRadius(5);
        diastolicDataSet.setValueTextSize(10);

        pulseDataSet.setColor(Color.RED);
        pulseDataSet.setDrawCircles(true);
        pulseDataSet.setCircleColor(Color.RED);
        pulseDataSet.setDrawCircleHole(true);
        pulseDataSet.setLineWidth(2);
        pulseDataSet.setCircleRadius(5);
        pulseDataSet.setValueTextSize(10);
    }

    @Override
    public void onPause() {
        super.onPause();
        getChartData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getChartData();
    }
}