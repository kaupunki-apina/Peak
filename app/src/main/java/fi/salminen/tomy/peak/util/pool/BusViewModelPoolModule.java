package fi.salminen.tomy.peak.util.pool;


import com.google.android.gms.maps.model.BitmapDescriptor;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.util.icons.BusIconMoving;
import fi.salminen.tomy.peak.util.icons.BusIconStationary;
import fi.salminen.tomy.peak.util.icons.MarkerIconModule;
import fi.salminen.tomy.peak.viewmodels.BusViewModel;

@Module(includes = MarkerIconModule.class)
public class BusViewModelPoolModule {

    @Provides
    BusViewModel provideBusViewModel(
            @BusIconMoving BitmapDescriptor moving,
            @BusIconStationary BitmapDescriptor stationary
    ) {
        return new BusViewModel(moving, stationary);
    }
}
