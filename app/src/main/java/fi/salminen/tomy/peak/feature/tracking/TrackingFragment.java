package fi.salminen.tomy.peak.feature.tracking;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

import javax.inject.Inject;

import butterknife.BindFloat;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.core.BaseFragment;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;
import fi.salminen.tomy.peak.inject.fragment.ForFragment;
import fi.salminen.tomy.peak.util.JsonValidator;
import fi.salminen.tomy.peak.util.Ui;
import fi.salminen.tomy.peak.viewmodels.MarkerTag;


public class TrackingFragment extends BaseFragment<TrackingFragmentComponent> implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Unbinder mUnbinder;
    private String mMapStyleJson;

    @BindView(R.id.mapView)
    MapView mMapView;

    @BindView(R.id.mapFragmentRoot)
    View root;

    @BindFloat(R.dimen.map_default_lat)
    float defaultLat;

    @BindFloat(R.dimen.map_default_lng)
    float defaultLng;

    @BindInt(R.integer.map_default_zoom)
    int defaultZoom;

    @Inject
    @ForFragment
    Context context;

    @Inject
    JsonValidator validator;

    @Inject
    MarkerManager mMarkerManager;

    public TrackingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray mAttrs = context.obtainStyledAttributes(attrs, R.styleable.TrackingFragment);
        mMapStyleJson = mAttrs.getString(R.styleable.TrackingFragment_map_style);
        mAttrs.recycle();
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
                .baseFragmentModule(new BaseFragmentModule(this))
                .peakApplicationComponent(PeakApplication.getApplication(getActivity()).component())
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mMarkerManager.dispose();
    }

    @Override
    public void inject() {
        component().inject(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        styleMap(mMap, mMapStyleJson);

        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(defaultLat, defaultLng), defaultZoom);
        mMap.animateCamera(center);

        UiSettings setting = this.mMap.getUiSettings();
        setting.setMapToolbarEnabled(false);

        if (Ui.isTranslucentStatusBar((AppCompatActivity) context)) {
            int padding = Ui.getStatusBarHeight(context);
            googleMap.setPadding(0, padding, 0, 0);
        }


        mMarkerManager.manage(mMap);
        mMap.setOnMarkerClickListener(marker -> {
            String text = ((MarkerTag) marker.getTag()).getInfoText();
            Snackbar.make(root, text, Snackbar.LENGTH_LONG).show();
            // Return false so default behaviour may occur
            return false;
        });
    }

    private void styleMap(GoogleMap googleMap, String styleJson) {
        if (validator.isValid(styleJson) && googleMap != null) {
            googleMap.setMapStyle(new MapStyleOptions(styleJson));
        }
    }
}
