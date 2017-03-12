package fi.salminen.tomy.peak.app;

import dagger.Module;
import fi.salminen.tomy.peak.inject.application.BaseApplicationModule;
import fi.salminen.tomy.peak.network.api.JourneysApiModule;

@Module(includes = {
        BaseApplicationModule.class,
        JourneysApiModule.class
})
public class PeakApplicationModule {
}
