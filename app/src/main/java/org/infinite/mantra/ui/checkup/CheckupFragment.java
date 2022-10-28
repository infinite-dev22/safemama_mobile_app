package org.infinite.mantra.ui.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.infinite.mantra.AddCheckupRecordActivity;
import org.infinite.mantra.BmiCalculatorActivity;
import org.infinite.mantra.MenstrualMonitorActivity;
import org.infinite.mantra.PregnancyMonitorActivity;
import org.infinite.mantra.R;
//import org.infinite.mantra.ui.add_record.AddRecordFragment;

public class CheckupFragment extends Fragment {

    FloatingActionButton addRecordFAB;
    View checkUpView;
    CardView bmi, pregnancy, menstrual;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initViews(inflater, container);
        clickActionEvents();
        return checkUpView;
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        checkUpView = inflater.inflate(R.layout.fragment_checkup, container, false);
        addRecordFAB = checkUpView.findViewById(R.id.add_record_fab);
        bmi = checkUpView.findViewById(R.id.bmi_calculator);
        pregnancy = checkUpView.findViewById(R.id.pregnancy_age_monitor);
        menstrual = checkUpView.findViewById(R.id.menstrual_monitor);
    }

    private void clickActionEvents() {
        addRecordFAB.setOnClickListener(view -> {
//                Intent addRecordIntent = new Intent(getActivity(), AddCheckupRecordActivity.class);
            startActivity(new Intent(getActivity(), AddCheckupRecordActivity.class));
        });

        bmi.setOnClickListener(view -> startActivity(new Intent(getActivity(), BmiCalculatorActivity.class)));

        pregnancy.setOnClickListener(view -> startActivity(new Intent(getActivity(), PregnancyMonitorActivity.class)));

        menstrual.setOnClickListener(view -> startActivity(new Intent(getActivity(), MenstrualMonitorActivity.class)));
    }
}