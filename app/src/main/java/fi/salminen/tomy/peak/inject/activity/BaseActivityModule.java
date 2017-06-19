package fi.salminen.tomy.peak.inject.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class BaseActivityModule {

    private final AppCompatActivity activity;

    public BaseActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ForActivity
    @ActivityScope
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    @ActivityScope
    AppCompatActivity provideActivity() {
        return activity;
    }
}
