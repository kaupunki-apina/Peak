package fi.salminen.tomy.peak.location;


import dagger.Module;
import fi.salminen.tomy.peak.inject.service.BaseServiceModule;
import fi.salminen.tomy.peak.network.api.JourneysApiModule;

@Module(includes = {BaseServiceModule.class, JourneysApiModule.class})
public class BusLocationServiceModule {

}
