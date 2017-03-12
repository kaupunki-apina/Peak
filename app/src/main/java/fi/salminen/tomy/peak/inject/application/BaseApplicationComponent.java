package fi.salminen.tomy.peak.inject.application;


import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = BaseApplicationModule.class)
@Singleton
public interface BaseApplicationComponent {

    Application getApplication();

    @ForApplication
    Context getApplicationContext();

}
