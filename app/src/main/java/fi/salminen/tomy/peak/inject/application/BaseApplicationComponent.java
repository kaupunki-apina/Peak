package fi.salminen.tomy.peak.inject.application;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = BaseApplicationModule.class)
public interface BaseApplicationComponent {

    Application getApplication();

    @ForApplication
    Context getApplicationContext();

}
