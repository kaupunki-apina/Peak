package fi.salminen.tomy.peak.feature.tracking;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;
import fi.salminen.tomy.peak.inject.fragment.ForFragment;

@Module(includes = {BaseFragmentModule.class, MarkerManagerModule.class})
class TrackingFragmentModule {

    @Provides
    MarkerManager provideMarkerManager(@ForFragment Context context) {
        return new MarkerManager(context);
    }
}
