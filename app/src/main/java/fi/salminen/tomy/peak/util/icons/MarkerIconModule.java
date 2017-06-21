package fi.salminen.tomy.peak.util.icons;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.inject.application.ForApplication;


@Module
public class MarkerIconModule {

    @Provides
    IconFactory providesIconFactory(@ForApplication Context context) {
        return new IconFactory(context, R.layout.bus_icon_label, R.layout.bus_icon_stationary, R.layout.bus_icon_moving);
    }
}
