package com.example.lennert.chiro_activitytracker.settingsActivity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.lennert.chiro_activitytracker.R;

/**
 * Created by Lennert on 11/04/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_startactivity);
    }
}
