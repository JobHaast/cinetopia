package nl.avans.cinetopia.presentation;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import nl.avans.cinetopia.R;

public class SettingsActivity extends PreferenceFragmentCompat {
    private String sessionId;
    private String watchedListId;
    private String watchListId;

    public SettingsActivity(String sessionId, String watchedListId, String watchListId){
        this.sessionId = sessionId;
        this.watchedListId = watchedListId;
        this.watchListId = watchListId;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_activity);
    }
}
