package fi.salminen.tomy.peak.app;

import dagger.Module;
import fi.salminen.tomy.peak.inject.application.BaseApplicationModule;

@Module(includes = {
        BaseApplicationModule.class
})
public class PeakApplicationModule {
}
