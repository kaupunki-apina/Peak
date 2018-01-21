package fi.salminen.tomy.peak.feature.tracking;


import dagger.Module;
import fi.salminen.tomy.peak.feature.tracking.map.MarkerManagerModule;
import fi.salminen.tomy.peak.inject.fragment.BaseFragmentModule;

@Module(includes = {BaseFragmentModule.class, MarkerManagerModule.class})
class TrackingFragmentModule {

}
