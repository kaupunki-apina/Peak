package fi.salminen.tomy.peak.util.pool;


import com.google.android.gms.maps.GoogleMap;

import fi.salminen.tomy.peak.persistence.models.BusModel;
import fi.salminen.tomy.peak.viewmodels.BusViewModel;

public class BusViewModelPool extends ViewModelPool<BusViewModel, BusModel> {
    private GoogleMap map;


    public BusViewModelPool(GoogleMap map) {
        this.map = map;
    }

    @Override
    BusViewModel newViewModel() {
        return new BusViewModel().setMap(map);
    }
}
