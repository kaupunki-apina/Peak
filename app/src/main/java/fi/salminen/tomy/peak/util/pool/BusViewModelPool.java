package fi.salminen.tomy.peak.util.pool;


import com.google.android.gms.maps.GoogleMap;

import fi.salminen.tomy.peak.persistence.models.BusModel;
import fi.salminen.tomy.peak.util.icons.MarkerIconModule;
import fi.salminen.tomy.peak.viewmodels.BusViewModel;

public class BusViewModelPool extends ViewModelPool<BusViewModel, BusModel> {
    private GoogleMap map;
    private BusViewModelPoolComponent component;

    public BusViewModelPool(GoogleMap map) {
        this.map = map;

        component = DaggerBusViewModelPoolComponent.builder()
                .busViewModelPoolModule(new BusViewModelPoolModule())
                .markerIconModule(new MarkerIconModule())
                .build();

    }

    @Override
    BusViewModel newViewModel() {
        return component.busViewModel().setMap(map);
    }
}
