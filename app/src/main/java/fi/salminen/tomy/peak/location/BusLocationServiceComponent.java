package fi.salminen.tomy.peak.location;


import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;
import fi.salminen.tomy.peak.inject.service.BaseServiceComponent;
import fi.salminen.tomy.peak.inject.service.ServiceScope;

@ServiceScope
@Component(
        dependencies = PeakApplicationComponent.class,
        modules = BusLocationServiceModule.class
)
public interface BusLocationServiceComponent extends BaseServiceComponent {
    void inject(BusLocationService service);
}
