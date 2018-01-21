package fi.salminen.tomy.peak.feature.tracking.map;


import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.persistence.PeakPrefs;

public abstract class MapManager<MapView extends View> {
    private Unbinder mUnbinder;
    private FlowContentObserver fco;
    private PeakPrefs prefs;

    @BindView(R.id.mapView)
    MapView mapView;

    /**
     * Constructs a new MapManager instance.
     *
     * Subclass should leverage Dagger 2's constructor injection to
     * acquire required instances of FlowContentObserver and PeakPrefs
     * and then pass them on to super class.
     */
    public MapManager(FlowContentObserver fco, PeakPrefs prefs) {
        this.fco = fco;
        this.prefs = prefs;
    }
    public MapView getMapView() {
        return this.mapView;
    }

    /**
     * Fragment lifecycle methods, should MapView need them.
     */

    @CallSuper
    public void onCreateView(View root) {
        mUnbinder = ButterKnife.bind(this, root);
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onLowMemory() {
    }

    @CallSuper
    public void onDestroyView() {
        mUnbinder.unbind();
    }

    public void onDestroy() {
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void onSaveInstanceState(Bundle outState) {
    }
}
