package fi.salminen.tomy.peak.activity.tracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.core.BaseActivity;
import fi.salminen.tomy.peak.inject.activity.BaseActivityModule;
import fi.salminen.tomy.peak.transport.BusLocationService;


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
                .peakApplicationComponent(PeakApplication.getApplication(this).component())
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(TrackingActivity.this, BusLocationService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(TrackingActivity.this, BusLocationService.class));
    }

    @Override
    public void inject() {
        component().inject(this);
    }
}
