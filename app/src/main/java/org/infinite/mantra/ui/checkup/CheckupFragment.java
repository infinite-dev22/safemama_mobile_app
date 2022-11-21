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

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.PointerType;

public class CheckupFragment extends Fragment {

    public static FloatingActionButton addRecordFAB;
    CardView bmi, pregnancy, menstrual, preeclampsia;
    String PREFERENCE_NAME = "PetographData";
    View parentView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_checkup, container, false);
        initViews(parentView);
        clickActionEvents();
        return parentView;
    }

    private void initViews(View view) {
        addRecordFAB = view.findViewById(R.id.add_record_fab);
        bmi = view.findViewById(R.id.bmi_calculator);
        pregnancy = view.findViewById(R.id.pregnancy_age_monitor);
        menstrual = view.findViewById(R.id.menstrual_monitor);
        preeclampsia = view.findViewById(R.id.add_record_cardview);
    }

    private void clickActionEvents() {
        addRecordFAB.setOnClickListener(view -> startActivity(new Intent(getActivity(), AddCheckupRecordActivity.class)));

        bmi.setOnClickListener(view -> startActivity(new Intent(getActivity(), BmiCalculatorActivity.class)));

        pregnancy.setOnClickListener(view -> startActivity(new Intent(getActivity(), PregnancyMonitorActivity.class)));

        menstrual.setOnClickListener(view -> startActivity(new Intent(getActivity(), MenstrualMonitorActivity.class)));

        preeclampsia.setOnClickListener(view -> startActivity(new Intent(getActivity(), AddCheckupRecordActivity.class)));
    }

    public void showIntro(String title, String text, View view) {
        new GuideView.Builder(getContext())
                .setTitle(title)
                .setContentText(text)
                .setTargetView(view)
                .setPointerType(PointerType.arrow)
                .setCircleIndicatorSize(10.0f)
                .setCircleInnerIndicatorSize(5.0f)
                .setCircleStrokeIndicatorSize(7.0f)
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setDismissType(DismissType.outsideTargetAndMessage) //optional - default dismissible by TargetView
                .setGuideListener(view1 -> {
                    if (view1.getId() == R.id.add_record_fab) showIntro("BMI Calculator", "Get your BMI value, your weight\nand height are used to calculate your BMI.", parentView.findViewById(R.id.bmi_calculator));
                    else if (view1.getId() == R.id.bmi_calculator) showIntro("Pregnancy Age", "Track your pregnancy age from here,\n date of child birth is also estimated.", parentView.findViewById(R.id.pregnancy_age_monitor));
                    else if (view1.getId() == R.id.pregnancy_age_monitor) showIntro("Menstrual cycle monitor", "Keep track of your period cycle here.", parentView.findViewById(R.id.menstrual_monitor));
                    else if (view1.getId() == R.id.menstrual_monitor) showIntro("Pre Eclampsia Management", "Add your vitals here to check for Pre-eclampsia.", parentView.findViewById(R.id.add_record_cardview));
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