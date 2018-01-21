package fi.salminen.tomy.peak.feature.tracking.map.mapbox;


import android.content.Context;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import javax.inject.Inject;

import fi.salminen.tomy.peak.BuildConfig;
import fi.salminen.tomy.peak.feature.tracking.map.MapManager;
import fi.salminen.tomy.peak.inject.fragment.ForFragment;
import fi.salminen.tomy.peak.persistence.PeakPrefs;


public class MapboxManager extends MapManager<MapView> {

    private MapboxMap mMapboxMap;

    @Inject
    public MapboxManager(@ForFragment Context context, FlowContentObserver fco, PeakPrefs prefs) {
        super(fco, prefs);
        Mapbox.getInstance(context, BuildConfig.MAPBOX_KEY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getMapView().onCreate(savedInstanceState);
        getMapView().getMapAsync(mapboxMap -> {
           this.mMapboxMap = mapboxMap;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getMapView().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMapView().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMapView().onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMapView().onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        getMapView().onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unbind called before this, may throw null pointer exception.
        getMapView().onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMapView().onSaveInstanceState(outState);
    }
}
