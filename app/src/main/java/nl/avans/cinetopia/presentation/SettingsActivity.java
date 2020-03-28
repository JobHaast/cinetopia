package nl.avans.cinetopia.presentation;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import nl.avans.cinetopia.R;

public class SettingsActivity extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_activity);
    }
}
