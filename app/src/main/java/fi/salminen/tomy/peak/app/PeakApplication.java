package fi.salminen.tomy.peak.app;

import android.support.annotation.NonNull;

import fi.salminen.tomy.peak.core.BaseApplication;
import fi.salminen.tomy.peak.inject.application.BaseApplicationModule;


public class PeakApplication extends BaseApplication<PeakApplicationComponent> {

    @Override
    public void inject() {
        component().inject(this);
    }

    @NonNull
    @Override
    protected PeakApplicationComponent createComponent() {
        return DaggerPeakApplicationComponent.builder()
                .baseApplicationModule(new BaseApplicationModule(this))
                .build();
    }
}
