package fi.salminen.tomy.peak.fragment.map;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.core.BaseFragment;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;


public class TrackingFragment extends BaseFragment<TrackingFragmentComponent> implements OnMapReadyCallback {


    @BindView(R.id.mapView)
    MapView mMapView;
    private Unbinder mUnbinder;

    public TrackingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_map, container, false);
        mUnbinder = ButterKnife.bind(this, layout);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // To display map instantly
        mMapView.getMapAsync(this);
        return layout;
    }


    @NonNull
    @Override
    protected TrackingFragmentComponent createComponent() {
        return DaggerTrackingFragmentComponent.builder()
                .baseFragmentModule(new BaseFragmentModule())
                .peakApplicationComponent(PeakApplication.getApplication(getActivity()).component())
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void inject() {
        component().inject(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
