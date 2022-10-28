package org.infinite.mantra;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class BmiCalculatorActivity extends AppCompatActivity {

    TextInputEditText bmi_height_txt, bmi_weight_txt;
    Button bmi_calculate_btn;
    TextView bmi_value_lbl, bmi_category_lbl;
    float bmi_value_float;
    String bmi_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        setActivityToolbar();
        initViews();
        clickActionEvents();
    }

    private void setActivityToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.toolbar_title_bmi_calculator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        bmi_value_lbl = findViewById(R.id.bmi_value_lbl);
        bmi_category_lbl = findViewById(R.id.bmi_category_lbl);
        bmi_height_txt = findViewById(R.id.bmi_height);
        bmi_weight_txt = findViewById(R.id.bmi_weight);
        bmi_calculate_btn = findViewById(R.id.btn_calculate_bmi);
    }

    private void clickActionEvents() {
        bmi_calculate_btn.setOnClickListener(view -> {
            float bmi_height = (Float.parseFloat(Objects.requireNonNull(bmi_height_txt.getText()).toString()) / 100.0f);
            float bmi_weight = Float.parseFloat(Objects.requireNonNull(bmi_weight_txt.getText()).toString());

            //BMI CALCULATE
            bmi_value_float = bmi_weight / (bmi_height * bmi_height);

            //LOGIC TO SET BMI VALUE AND CATEGORY AND BMI VALUE COLOR
            bmi_value = String.format("%.2f", bmi_value_float);
            bmi_value_lbl.setText(bmi_value);
            setColors(bmi_value_float);
        });
    }

    public void setColors(float bmi_value_float) {
        if (bmi_value_float < 16.0f) {
            bmi_category_lbl.setText(R.string.bmi_cat_severly_underweight);
            bmi_category_lbl.setTextColor(getResources().getColor(R.color.bmi_color_severly_underweight));
        } else if (bmi_value_float < 18.5f) {
            bmi_category_lbl.setText(R.string.bmi_cat_underweight);
            bmi_category_lbl.setTextColor(getResources().getColor(R.color.bmi_color_underweight));
        } else if (bmi_value_float >= 18.5f && bmi_value_float <= 24.9f) {
            bmi_category_lbl.setText(R.string.bmi_cat_normal);
            bmi_category_lbl.setTextColor(getResources().getColor(R.color.bmi_color_normal));
        } else if (bmi_value_float >= 25.0f && bmi_value_float <= 29.9f) {
            bmi_category_lbl.setText(R.string.bmi_cat_overweight);
            bmi_category_lbl.setTextColor(getResources().getColor(R.color.bmi_color_overweight));
        } else if (bmi_value_float >= 30.0f && bmi_value_float <= 34.9f) {
            bmi_category_lbl.setText(R.string.bmi_cat_severly_overweight);
            bmi_category_lbl.setTextColor(getResources().getColor(R.color.bmi_color_severly_overweight));
        } else if (bmi_value_float >= 35.0f && bmi_value_float <= 39.9f) {
            bmi_category_lbl.setText(R.string.bmi_cat_obese);
            bmi_category_lbl.setTextColor(getResources().getColor(R.color.bmi_color_obese));
        } else {
            bmi_category_lbl.setText(R.string.bmi_cat_severely_obese);
            bmi_category_lbl.setTextColor(getResources().getColor(R.color.bmi_color_severely_obese));
        }
    }

}