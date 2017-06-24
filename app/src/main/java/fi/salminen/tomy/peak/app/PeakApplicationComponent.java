package fi.salminen.tomy.peak.app;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import javax.inject.Singleton;

import dagger.Component;
import fi.salminen.tomy.peak.inject.application.BaseApplicationComponent;
import fi.salminen.tomy.peak.inject.application.ForApplication;
import fi.salminen.tomy.peak.persistence.DBUtil;
import fi.salminen.tomy.peak.persistence.PeakPrefs;

@Singleton
@Component(modules = PeakApplicationModule.class)
public interface PeakApplicationComponent extends BaseApplicationComponent {

    Application getApplication();

    @ForApplication
    Context getApplicationContext();

    DBUtil getDBUtil();

    FlowContentObserver getFlowContentObserver();

    PeakPrefs getPeakPrefs();

    void inject(PeakApplication application);
}
