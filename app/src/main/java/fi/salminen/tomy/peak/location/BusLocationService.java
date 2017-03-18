package fi.salminen.tomy.peak.location;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.core.BaseService;
import fi.salminen.tomy.peak.service.bus.DaggerBusLocationServiceComponent;

public class BusLocationService extends BaseService<BusLocationServiceComponent> {



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Started service, cannot be bound.
        return null;
    }

    @NonNull
    @Override
    protected BusLocationServiceComponent createComponent() {
        return DaggerBusLocationServiceComponent.builder()
                .peakApplicationComponent(PeakApplication.getApplication(this).component())
                .build();
    }

    @Override
    public void inject() {
        component().inject(this);
    }

}
