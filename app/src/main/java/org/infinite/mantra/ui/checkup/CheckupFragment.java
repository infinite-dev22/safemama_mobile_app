package org.infinite.mantra.ui.checkup;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Objects;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;
//import org.infinite.mantra.ui.add_record.AddRecordFragment;

public class CheckupFragment extends Fragment {

    public static FloatingActionButton addRecordFAB;
    View checkUpView;
    CardView bmi, pregnancy, menstrual, preeclampsia;
    String PREFERENCE_NAME = "PetographData";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        preeclampsia = checkUpView.findViewById(R.id.add_record_cardview);
    }

    private void clickActionEvents() {
        addRecordFAB.setOnClickListener(view -> startActivity(new Intent(getActivity(), AddCheckupRecordActivity.class)));

        bmi.setOnClickListener(view -> startActivity(new Intent(getActivity(), BmiCalculatorActivity.class)));

        pregnancy.setOnClickListener(view -> startActivity(new Intent(getActivity(), PregnancyMonitorActivity.class)));

        menstrual.setOnClickListener(view -> startActivity(new Intent(getActivity(), MenstrualMonitorActivity.class)));

        preeclampsia.setOnClickListener(view -> startActivity(new Intent(getActivity(), AddCheckupRecordActivity.class)));
    }

    public void showIntro(String title, String text, View view) {
        new GuideView.Builder(getContext()).setTitle(title).setContentText(text).setTargetView(view).setPointerType(PointerType.arrow).setCircleIndicatorSize(10.0f).setCircleInnerIndicatorSize(5.0f).setCircleStrokeIndicatorSize(7.0f).setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setDismissType(DismissType.outsideTargetAndMessage) //optional - default dismissible by TargetView
                .setGuideListener(view1 -> {
                    switch (view1.getId()) {
                        case R.id.add_record_fab:
                            showIntro("BMI Calculator", "Get your BMI value, your weight\nand height are used to calculate your BMI.", checkUpView.findViewById(R.id.bmi_calculator));
                            break;
                        case R.id.bmi_calculator:
                            showIntro("Pregnancy Age", "Track your pregnancy age from here,\n date of child birth is also estimated.", checkUpView.findViewById(R.id.pregnancy_age_monitor));
                            break;
                        case R.id.pregnancy_age_monitor:
                            showIntro("Menstrual cycle monitor", "Keep track of your period cycle here.", checkUpView.findViewById(R.id.menstrual_monitor));
                            break;
                        case R.id.menstrual_monitor:
                            showIntro("Pre Eclampsia Management", "Add your vitals here to check for Pre-eclampsia.", checkUpView.findViewById(R.id.add_record_cardview));
                            break;
                    }
                }).build().show();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);

        boolean firstTime = sharedPreferences.getBoolean("firstTimeRun", true);
        if (firstTime) {
            showIntro("It's easy to use", "Click this button to add your vital signs,\n these will be used to check for Pre-Eclampsia.", addRecordFAB);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTimeRun", false);
            editor.apply();
        }
    }
}