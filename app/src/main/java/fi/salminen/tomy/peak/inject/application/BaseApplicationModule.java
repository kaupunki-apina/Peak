package fi.salminen.tomy.peak.inject.application;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.util.JsonValidator;

@Module
public class BaseApplicationModule {

    private final Application application;

    public BaseApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    JsonValidator provideJsonValidator() {
        return new JsonValidator();
    }
}
