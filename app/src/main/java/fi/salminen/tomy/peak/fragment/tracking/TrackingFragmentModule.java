package fi.salminen.tomy.peak.fragment.tracking;


import android.content.Context;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;
import fi.salminen.tomy.peak.inject.fragment.ForFragment;

@Module(includes = BaseFragmentModule.class)
class TrackingFragmentModule {

    @Provides
    MarkerManager provideMarkerManager(@ForFragment Context context, FlowContentObserver fco) {
        return new MarkerManager(context, fco);
    }
}
