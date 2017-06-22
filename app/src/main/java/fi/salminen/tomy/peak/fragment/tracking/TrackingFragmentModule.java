package fi.salminen.tomy.peak.fragment.tracking;


import android.content.Context;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;
import fi.salminen.tomy.peak.inject.fragment.ForFragment;
import fi.salminen.tomy.peak.util.MarkerManager;
import fi.salminen.tomy.peak.util.icons.IconFactory;
import fi.salminen.tomy.peak.util.icons.MarkerIconModule;

@Module(includes = {BaseFragmentModule.class, MarkerIconModule.class})
class TrackingFragmentModule {

    @Provides
    MarkerManager provideMarkerManager(@ForFragment Context context, FlowContentObserver fco, IconFactory iconFactory) {
        return new MarkerManager(context, fco, iconFactory);
    }
}
