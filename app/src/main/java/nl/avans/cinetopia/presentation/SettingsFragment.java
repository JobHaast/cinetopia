package nl.avans.cinetopia.presentation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import nl.avans.cinetopia.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    private String sessionId;
    private String watchedListId;
    private String watchListId;

    public SettingsFragment(String sessionId, String watchedListId, String watchListId){
        this.sessionId = sessionId;
        this.watchedListId = watchedListId;
        this.watchListId = watchListId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Confirm this fragment has menu items.
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);
        MenuItem itemFilter = menu.findItem(R.id.action_filter);
        if (itemSearch != null) {
            itemSearch.setVisible(false);
        }
        if (itemFilter != null){
            itemFilter.setVisible(false);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_activity);
    }
}
