package fi.salminen.tomy.peak.activities.tracking;

import android.os.Bundle;
import android.support.annotation.NonNull;

import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.core.BaseActivity;
import fi.salminen.tomy.peak.inject.activity.BaseActivityModule;


public class TrackingActivity extends BaseActivity<TrackingActivityComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
    }

    @NonNull
    @Override
    protected TrackingActivityComponent createComponent() {
        return DaggerTrackingActivityComponent.builder()
                .baseActivityModule(new BaseActivityModule(this))
                .peakApplicationComponent(((PeakApplication) getApplication()).component())
                .build();
    }

    @Override
    public void inject() {
        component().inject(this);
    }
}
