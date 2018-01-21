package fi.salminen.tomy.peak.feature.tracking.map;


import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fi.salminen.tomy.peak.R;

public abstract class MapManager<MapView extends View> {
    private Unbinder mUnbinder;
    @BindView(R.id.mapView)
    MapView mapView;

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
