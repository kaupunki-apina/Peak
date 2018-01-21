package fi.salminen.tomy.peak.feature.tracking;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.core.BaseFragment;
import fi.salminen.tomy.peak.feature.tracking.map.mapbox.MapboxManager;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;
import fi.salminen.tomy.peak.inject.fragment.ForFragment;


public class TrackingFragment extends BaseFragment<TrackingFragmentComponent> /* implements OnMapReadyCallback */ {
    private Unbinder mUnbinder;

    // @BindView(R.id.mapView)
    // MapView mMapView;

    @Inject
    @ForFragment
    Context context;

    @Inject
    MapboxManager mMapManager;

    public TrackingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_map, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        mMapManager.onCreateView(layout);
        return layout;
    }


    @NonNull
    @Override
    protected TrackingFragmentComponent createComponent() {
        return DaggerTrackingFragmentComponent.builder()
                .baseFragmentModule(new BaseFragmentModule(this))
                .peakApplicationComponent(PeakApplication.getApplication(getActivity()).component())
                .build();
    }

    @Override
    public void inject() {
        component().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mMapManager.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapManager.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapManager.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapManager.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapManager.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapManager.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapManager.onSaveInstanceState(outState);
    }
}
