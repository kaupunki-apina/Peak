package fi.salminen.tomy.peak.util.pool;


import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.persistence.models.BusModel;
import fi.salminen.tomy.peak.util.icons.MarkerIconModule;
import fi.salminen.tomy.peak.viewmodels.BusViewModel;

public class BusViewModelPool extends ViewModelPool<BusViewModel, BusModel> {
    private GoogleMap map;
    private BusViewModelPoolComponent component;

    public BusViewModelPool(GoogleMap map, Context context) {
        this.map = map;

        component = DaggerBusViewModelPoolComponent.builder()
                .busViewModelPoolModule(new BusViewModelPoolModule())
                .peakApplicationComponent(PeakApplication.getApplication(context).component())
                .markerIconModule(new MarkerIconModule())
                .build();

    }

    @Override
    BusViewModel newViewModel() {
        return component.busViewModel().setMap(map);
    }
}
