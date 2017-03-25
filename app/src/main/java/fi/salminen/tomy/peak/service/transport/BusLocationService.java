package fi.salminen.tomy.peak.service.transport;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import fi.salminen.tomy.peak.app.PeakApplication;
import fi.salminen.tomy.peak.core.BaseService;
import fi.salminen.tomy.peak.inject.service.BaseServiceModule;
import fi.salminen.tomy.peak.models.Bus;
import fi.salminen.tomy.peak.network.api.JourneysApi;
import fi.salminen.tomy.peak.util.SubscriberAdapter;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class BusLocationService extends BaseService<BusLocationServiceComponent> {

    @Inject
    JourneysApi mApi;
    private Subscription mSubscription;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSubscription = Observable.interval(3, TimeUnit.SECONDS) // TODO Dynamic interval
                .flatMap(tick -> mApi.getBuses())
                .doOnError(throwable -> emitError(throwable))
                .retry()
                .observeOn(Schedulers.io())
                .subscribe(new SubscriberAdapter<List<Bus>>() {
                    @Override
                    public void onNext(List<Bus> buses) {
                        // TODO
                        // Save locally
                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }

    private void emitError(Throwable throwable) {
        // TODO
    }

    public IBinder onBind(Intent intent) {
        // Started service, cannot be bound.
        return null;
    }


    @NonNull
    @Override
    protected BusLocationServiceComponent createComponent() {
        return DaggerBusLocationServiceComponent.builder()
                .peakApplicationComponent(PeakApplication.getApplication(this).component())
                .baseServiceModule(new BaseServiceModule(this))
                .build();
    }

    @Override
    public void onDestroy() {
        if (!mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void inject() {
        component().inject(this);
    }

}
