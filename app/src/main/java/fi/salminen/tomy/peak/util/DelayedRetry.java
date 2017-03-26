package fi.salminen.tomy.peak.util;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class DelayedRetry implements Function<Observable<? extends Throwable>, Observable<?>> {
    private int mAttempts = 0;

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap((Function<Throwable, Observable<?>>) throwable -> {
            mAttempts++;
            return Observable.timer(mAttempts * mAttempts, TimeUnit.SECONDS);
        });
    }

    public void reset() {
        mAttempts = 0;
    }
}
