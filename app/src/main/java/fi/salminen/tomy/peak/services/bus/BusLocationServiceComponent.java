package fi.salminen.tomy.peak.services.bus;


import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;

@BusServiceScope
@Component(dependencies = PeakApplicationComponent.class)
public interface BusLocationServiceComponent {

    void inject(BusLocationService service);
}
