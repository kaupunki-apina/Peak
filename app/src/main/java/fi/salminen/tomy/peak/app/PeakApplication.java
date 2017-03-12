package fi.salminen.tomy.peak.app;

import android.support.annotation.NonNull;

import fi.salminen.tomy.peak.core.BaseApplication;


public class PeakApplication extends BaseApplication<PeakApplicationComponent> {

    @Override
    public void inject() {
        component().inject(this);
    }

    @NonNull
    @Override
    protected PeakApplicationComponent createComponent() {
        return DaggerPeakApplicationComponent.builder()
                .peakApplicationModule(new PeakApplicationModule())
                .build();
    }
}
