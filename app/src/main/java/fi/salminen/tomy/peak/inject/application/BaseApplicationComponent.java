package fi.salminen.tomy.peak.inject.application;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import fi.salminen.tomy.peak.persistence.DBUtil;


@Singleton
@Component(modules = BaseApplicationModule.class)
public interface BaseApplicationComponent {

    Application getApplication();

    DBUtil getDBUtil();

    @ForApplication
    Context getApplicationContext();

}
