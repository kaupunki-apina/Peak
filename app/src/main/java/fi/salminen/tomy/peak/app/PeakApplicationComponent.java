package fi.salminen.tomy.peak.app;

import dagger.Component;

@Component(modules = PeakApplicationModule.class)
public interface PeakApplicationComponent {

    void inject(PeakApplication application);
}
