package org.infinite.mantra.ui.preferences;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import org.infinite.mantra.R;

public class UserPreferenceFragment extends PreferenceFragmentCompat {

    SwitchPreferenceCompat night_mode, notifications;
    Preference exit_app;
    String PREFERENCE_NAME = "PetographData";

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        initViews();
        clickActionEvents();
    }

    private void clickActionEvents() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        night_mode.setOnPreferenceChangeListener((preference, newSwitchValue) -> {
            boolean checked = (boolean) newSwitchValue;

            if (checked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("night_mode", true).apply();
                night_mode.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("night_mode", false).apply();
                night_mode.setChecked(false);
            }
            return false;
        });

        notifications.setOnPreferenceChangeListener((preference, newSwitchValue) -> {
            boolean checked = (boolean) newSwitchValue;

            if (checked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("notify_user", true).apply();
                notifications.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("notify_user", false).apply();
                notifications.setChecked(false);
            }
            return false;
        });

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
    }

    public void getStoredPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);

        boolean dark_theme = sharedPreferences.getBoolean("night_mode", false);
        boolean notify = sharedPreferences.getBoolean("notify_user", true);

        if (dark_theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getStoredPreferences();
    }
}
