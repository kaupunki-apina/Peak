package fi.salminen.tomy.peak.app;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import fi.salminen.tomy.peak.inject.application.BaseApplicationComponent;
import fi.salminen.tomy.peak.inject.application.ForApplication;

@Singleton
@Component(modules = PeakApplicationModule.class)
public interface PeakApplicationComponent extends BaseApplicationComponent {

    Application getApplication();

    @ForApplication
    Context getApplicationContext();

    void inject(PeakApplication application);
}
