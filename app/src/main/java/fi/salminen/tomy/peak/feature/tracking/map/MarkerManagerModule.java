package fi.salminen.tomy.peak.feature.tracking.map;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.R;


@Module
public class MarkerManagerModule {

    private Context context;

    public MarkerManagerModule(Context context) {
        this.context = context;
    }

    @Provides
    IconFactory provideIconFactory() {
        return new IconFactory(R.layout.bus_icon_label, R.layout.bus_icon_stationary, R.layout.bus_icon_moving);
    }
}
