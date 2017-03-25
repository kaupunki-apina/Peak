package fi.salminen.tomy.peak.util;

import rx.Subscriber;



public abstract class SubscriberAdapter<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        // Adapter method stub
    }

    @Override
    public void onError(Throwable e) {
        // Adapter method stub
    }

    @Override
    public void onNext(T t) {
        // Adapter method stub
    }
}
