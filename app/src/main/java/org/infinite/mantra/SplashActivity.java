package org.infinite.mantra;

import static org.infinite.mantra.ui.checkup.CheckupFragment.addRecordFAB;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.infinite.mantra.ui.preferences.UserPreferenceFragment;

public class SplashActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private final Runnable runnable = SplashActivity.this::openMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash);
        this.handler.postDelayed(this.runnable, 3000);

    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
