package fi.salminen.tomy.peak.persistence;

import android.content.SharedPreferences;

import net.orange_box.storebox.annotations.method.KeyByResource;

import java.util.Set;

import fi.salminen.tomy.peak.R;


public interface PeakPrefs extends SharedPreferences {

    @KeyByResource(R.string.prefs_select_lines_key)
    Set<String> getSelectedLines();

    @KeyByResource(R.string.prefs_select_lines_key)
    void setSelectedLines(Set<String> lines);
}

