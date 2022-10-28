package org.infinite.mantra.ui.preferences;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import org.infinite.mantra.R;

public class UserPreferenceFragment extends PreferenceFragmentCompat {

    SwitchPreferenceCompat night_mode, notifications;
    Preference exit_app;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        initViews();
        clickActionEvents();
        getStoredPreferences(savedInstanceState);
        getViewSateValue();
    }

    private void clickActionEvents() {
        night_mode.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean checked = (boolean) newValue;

            if (editor != null) editor.clear().apply();

            if (checked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("night_mode", true);
                night_mode.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("night_mode", false);
                night_mode.setChecked(false);
            }
            return false;
        });

//        notifications.setOnPreferenceChangeListener((preference, newValue) -> {
//            boolean checked = (boolean) newValue;
//
//            if (editor != null) editor.clear().apply();
//
//            if (checked) {
//                notifications.setChecked(true);
//            } else {
//                notifications.setChecked(false);
//            }
//            return true;
//        });

        exit_app.setOnPreferenceClickListener(preference -> {
            finishAffinity(requireActivity());
            System.exit(0);
            return true;
        });
    }

    public void initViews() {
        night_mode = findPreference("night_mode");
        notifications = findPreference("notifications");
        exit_app = findPreference("exit");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        editor = sharedPreferences.edit();
    }

    public void getStoredPreferences(Bundle savedInstance) {

        if (savedInstance != null) {
            boolean theme = savedInstance.getBoolean("night_mode");
            boolean notify = savedInstance.getBoolean("notifications");
            if (theme) {
                night_mode.setChecked(savedInstance.getBoolean("night_mode"));
            }
            if (notify) {
                notifications.setChecked(savedInstance.getBoolean("notifications"));
            }
        }
    }

    public void getViewSateValue() {

        if (night_mode.isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

//        if (notifications.isChecked()) {
//        } else {
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        editor.putBoolean("notifications", true);
        editor.putBoolean("notifications", false);
        editor.apply();
        editor.commit();
    }
}
