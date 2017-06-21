package fi.salminen.tomy.peak.util.icons;


import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.R;

@Module
public class MarkerIconModule {

    @Provides
    @BusIconStationary
    BitmapDescriptor provideBusIconStationary() {
        return BitmapDescriptorFactory.fromResource(R.drawable.tmp);
    }

    @Provides
    @BusIconMoving
    BitmapDescriptor provideBusIconMoving() {
        return BitmapDescriptorFactory.fromResource(R.drawable.tmp2);
    }
}
