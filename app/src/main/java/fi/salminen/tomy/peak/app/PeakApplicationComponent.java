package fi.salminen.tomy.peak.app;

import javax.inject.Singleton;

import dagger.Component;
import fi.salminen.tomy.peak.inject.application.BaseApplicationComponent;

@Singleton
@Component(modules = PeakApplicationModule.class)
public interface PeakApplicationComponent extends BaseApplicationComponent {
    // TODO
    //
    // Include ALL downstreamable components here.
    void inject(PeakApplication application);
}
