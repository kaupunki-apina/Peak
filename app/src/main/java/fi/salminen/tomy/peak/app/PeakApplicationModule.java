package fi.salminen.tomy.peak.app;

import dagger.Module;
import fi.salminen.tomy.peak.inject.application.BaseApplicationModule;
import fi.salminen.tomy.peak.network.api.JourneysApiModule;
import fi.salminen.tomy.peak.persistence.PersistenceModule;

@Module(includes = {
        BaseApplicationModule.class,
        JourneysApiModule.class,
        PersistenceModule.class
})
public class PeakApplicationModule {
}
