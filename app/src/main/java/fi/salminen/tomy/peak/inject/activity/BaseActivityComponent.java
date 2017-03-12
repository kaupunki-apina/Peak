package fi.salminen.tomy.peak.inject.activity;

import android.app.Activity;
import android.content.Context;

import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;


@ActivityScope
@Component(dependencies = PeakApplicationComponent.class, modules = BaseActivityModule.class)
public interface BaseActivityComponent {

    Activity getActivity();

    @ForActivity
    Context getActivityContext();
}
