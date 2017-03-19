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
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class BusLocationService extends BaseService<BusLocationServiceComponent> {

    @Inject
    JourneysApi mApi;

    private Subscription mSub;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSub = Observable.interval(3, TimeUnit.SECONDS) // TODO Dynamic interval
                .map(tick -> mApi.getBuses())
                .retry()
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Observable<List<Bus>>>() {
                    @Override
                    public void onCompleted() {
                        BusLocationService.this.stopSelf();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO User error message.
                    }

                    @Override
                    public void onNext(Observable<List<Bus>> listObservable) {
                        // TODO Save locally.
                    }
                });
        return super.onStartCommand(intent, flags, startId);
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
        if (!mSub.isUnsubscribed()) mSub.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void inject() {
        component().inject(this);
    }

}
