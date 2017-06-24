package fi.salminen.tomy.peak.feature.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import fi.salminen.tomy.peak.R;


public class GeneralPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        setHasOptionsMenu(false);
    }
}