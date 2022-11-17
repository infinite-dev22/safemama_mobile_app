package org.infinite.mantra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private final Runnable runnable = SplashActivity.this::openMain;
    String PREFERENCE_NAME = "PetographData";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = this.getWindow();
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        this.handler.postDelayed(this.runnable, 1500);
        getStoredPreferences();
    }

    public void getStoredPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);

        boolean dark_theme = sharedPreferences.getBoolean("night_mode", false);
        if (dark_theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
