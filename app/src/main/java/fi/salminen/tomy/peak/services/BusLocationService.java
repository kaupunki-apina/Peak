package fi.salminen.tomy.peak.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BusLocationService extends Service {
    public BusLocationService() {
        // TODO
        // Dependency injections.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Started service, cannot be bound.
        return null;
    }
}
