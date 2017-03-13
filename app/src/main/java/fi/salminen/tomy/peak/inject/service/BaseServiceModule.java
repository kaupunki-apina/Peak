package fi.salminen.tomy.peak.inject.service;

import android.app.Service;
import android.content.Context;

import dagger.Module;
import dagger.Provides;


@Module
public class BaseServiceModule {

    private final Service service;

    public BaseServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    @ServiceScope
    @ForService
    public Context providesServiceContext() {
        return service;
    }

}
