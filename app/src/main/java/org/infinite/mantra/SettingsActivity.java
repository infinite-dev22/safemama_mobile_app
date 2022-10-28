package org.infinite.mantra;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.infinite.mantra.ui.preferences.UserPreferenceFragment;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private UserPreferenceFragment userPreferenceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        initComponents();
        fillInFragments();
    }

    private void fillInFragments() {
        getSupportFragmentManager().beginTransaction().replace(R.id.preference_container, userPreferenceFragment).commit();
    }

    private void initComponents() {
        userPreferenceFragment = new UserPreferenceFragment();
    }
}