package fi.salminen.tomy.peak.inject.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;


@ActivityScope
@Component(dependencies = PeakApplicationComponent.class, modules = BaseActivityModule.class)
public interface BaseActivityComponent {

    AppCompatActivity getActivity();

    @ForActivity
    Context getActivityContext();
}
