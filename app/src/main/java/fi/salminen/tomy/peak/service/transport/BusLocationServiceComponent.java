package fi.salminen.tomy.peak.service.transport;


import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;
import fi.salminen.tomy.peak.inject.service.BaseServiceComponent;
import fi.salminen.tomy.peak.inject.service.ServiceScope;
import fi.salminen.tomy.peak.network.api.JourneysApi;

@ServiceScope
@Component(
        dependencies = PeakApplicationComponent.class,
        modules = BusLocationServiceModule.class
)
public interface BusLocationServiceComponent extends BaseServiceComponent {
    JourneysApi getJourneysApi();

    void inject(BusLocationService service);
}
