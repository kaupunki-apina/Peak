package fi.salminen.tomy.peak.feature.tracking;


import dagger.Component;
import fi.salminen.tomy.peak.app.PeakApplicationComponent;
import fi.salminen.tomy.peak.inject.activity.ActivityScope;
import fi.salminen.tomy.peak.inject.activity.BaseActivityComponent;


@ActivityScope
@Component(dependencies = PeakApplicationComponent.class, modules = TrackingActivityModule.class)
public interface TrackingActivityComponent extends BaseActivityComponent{

    void inject(TrackingActivity activity);
}
