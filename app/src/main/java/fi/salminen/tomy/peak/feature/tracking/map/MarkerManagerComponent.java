package fi.salminen.tomy.peak.feature.tracking.map;


import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;

@MarkerManagerScope
@Component(dependencies = PeakApplicationComponent.class, modules = MarkerManagerModule.class)
public interface MarkerManagerComponent {

    IconFactory getIconFactory();

    void inject(MarkerManager manager);
}

