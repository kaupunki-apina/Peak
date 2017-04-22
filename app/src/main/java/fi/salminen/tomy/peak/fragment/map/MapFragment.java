package fi.salminen.tomy.peak.fragment.map;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.core.BaseFragment;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;


public class MapFragment extends BaseFragment<MapFragmentComponent> {


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @NonNull
    @Override
    protected MapFragmentComponent createComponent() {
        return DaggerMapFragmentComponent.builder()
                .baseFragmentModule(new BaseFragmentModule())
                .peakApplicationComponent(PeakApplication.getApplication(getActivity()).component())
                .build();
    }

    @Override
    public void inject() {
        component().inject(this);
    }
}
