package org.infinite.mantra;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.infinite.mantra.database.dao.PetographDAO;
import org.infinite.mantra.database.model.PetographModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BmiCalculatorActivity extends AppCompatActivity {

    TextInputEditText bmi_height_txt, bmi_weight_txt;
    Button bmi_calculate_btn;
    TextView bmi_value_lbl, bmi_category_lbl, previous_bmi_value_lbl;
    float bmi_value_float;
    String bmi_value, bmi_height, bmi_weight;
    View focusView = null;
    PetographDAO dao;
    List<PetographModel> dbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        setActivityToolbar();
        initViews();
        clickActionEvents();
        setData();
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
        dao = new PetographDAO(getApplicationContext());
        dbList = new ArrayList<>();
        previous_bmi_value_lbl = findViewById(R.id.previous_bmi_value);
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

            // SAVE BMI VALUES TO DATABASE
            saveBMIData();
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

    public boolean checkEmpty() {
        bmi_height = Objects.requireNonNull(bmi_height_txt.getText()).toString();
        bmi_weight = Objects.requireNonNull(bmi_weight_txt.getText()).toString();

        if (TextUtils.isEmpty(bmi_height)) {
            bmi_weight_txt.setError(getString(R.string.error_field_required));
            focusView = bmi_height_txt;
            return false;
        } else if (TextUtils.isEmpty(bmi_weight)) {
            bmi_weight_txt.setError(getString(R.string.error_field_required));
            focusView = bmi_weight_txt;
            return false;
        } else {
            return true;
        }
    }

    public void saveBMIData() {
        bmi_height = Objects.requireNonNull(bmi_height_txt.getText()).toString();
        bmi_weight = Objects.requireNonNull(bmi_weight_txt.getText()).toString();

        if (checkEmpty()) {
            PetographModel dbModel = new PetographModel();

            dbModel.setUserHeight(bmi_height);
            dbModel.setUserWeight(bmi_weight);
            dbModel.setBmi(bmi_value);

            dao.insertBMI(bmi_height, bmi_weight, bmi_value);

            bmi_height_txt.setText("");
            bmi_weight_txt.setText("");
        }
    }

    private void setData() {
        dbList = dao.getBMIData();
        if (dbList.size() != 0) {
            int curValue = dbList.size() - 1;
            String bmi = dbList.get(curValue).getBmi();
            previous_bmi_value_lbl.setText(bmi);
            setColors(Float.parseFloat(bmi));
            System.out.println(bmi);
        }
    }

}