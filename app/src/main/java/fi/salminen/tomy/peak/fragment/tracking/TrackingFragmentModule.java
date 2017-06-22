package fi.salminen.tomy.peak.fragment.tracking;


import android.content.Context;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;
import fi.salminen.tomy.peak.inject.fragment.ForFragment;
import fi.salminen.tomy.peak.util.MarkerManager;
import fi.salminen.tomy.peak.util.IconFactory;

@Module(includes = BaseFragmentModule.class)
class TrackingFragmentModule {
    @Provides
    IconFactory provideIconFactory(@ForFragment Context context) {
        return new IconFactory(context, R.layout.bus_icon_label, R.layout.bus_icon_stationary, R.layout.bus_icon_moving);
    }

    @Provides
    MarkerManager provideMarkerManager(@ForFragment Context context, FlowContentObserver fco, IconFactory iconFactory) {
        return new MarkerManager(context, fco, iconFactory);
    }
}
