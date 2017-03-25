package fi.salminen.tomy.peak.util;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public class DelayedRetry implements Func1<Observable<? extends Throwable>, Observable<?>> {
    private int mAttempts = 0;

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
            mAttempts++;
            return Observable.timer(mAttempts * mAttempts, TimeUnit.SECONDS);
        });
    }

    public void reset() {
        mAttempts = 0;
    }
}
