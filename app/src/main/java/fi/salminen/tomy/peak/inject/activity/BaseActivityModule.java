package fi.salminen.tomy.peak.inject.activity;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;


@Module
public class BaseActivityModule {

    private final Activity activity;

    public BaseActivityModule(Activity activity) {
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
    Activity provideActivity() {
        return activity;
    }
}
