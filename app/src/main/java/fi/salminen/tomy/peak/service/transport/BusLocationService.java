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
import fi.salminen.tomy.peak.network.api.JourneysApi;
import fi.salminen.tomy.peak.persistence.DBUtil;
import fi.salminen.tomy.peak.persistence.models.bus.BusModel;
import fi.salminen.tomy.peak.util.DelayedRetry;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


public class BusLocationService extends BaseService<BusLocationServiceComponent> {

    @Inject
    JourneysApi mApi;
    @Inject
    DBUtil mDbUtil;
    private Disposable mSubscription;
    private DelayedRetry mRetry = new DelayedRetry();
    public static final int DELAY = 3; // TODO value from prefs
    private PublishSubject<Throwable> mErrorSubject = PublishSubject.create();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSubscription = mApi.getBuses()
                .repeatWhen(o -> o.delay(DELAY, TimeUnit.SECONDS))
                .doOnError(mErrorSubject::onNext)
                .retryWhen(mRetry)
                .observeOn(Schedulers.io())
                .subscribe(this::onNext);

        return super.onStartCommand(intent, flags, startId);
    }


    private void onNext(List<BusModel> buses) {
        mRetry.reset(); // Reset escalation counter
        mDbUtil.save(buses);
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
        if (!mSubscription.isDisposed()) mSubscription.dispose();
        super.onDestroy();
    }

    @Override
    public void inject() {
        component().inject(this);
    }

    public Observable<Throwable> getErrorObservable() {
        return (Observable) mErrorSubject;
    }
}
