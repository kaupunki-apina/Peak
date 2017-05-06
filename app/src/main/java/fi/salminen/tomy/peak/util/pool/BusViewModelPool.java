package fi.salminen.tomy.peak.util.pool;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;

import fi.salminen.tomy.peak.persistence.models.bus.BusModel;
import fi.salminen.tomy.peak.persistence.models.bus.BusViewModel;

public class BusViewModelPool extends ViewModelPool<BusViewModel, BusModel> {
    private GoogleMap map;

    public BusViewModelPool(@NonNull Context context, GoogleMap map) {
        super(context);
        this.map = map;
    }

    @Override
    BusViewModel newViewModel(Context context) {
        return new BusViewModel(context, map);
    }
}
