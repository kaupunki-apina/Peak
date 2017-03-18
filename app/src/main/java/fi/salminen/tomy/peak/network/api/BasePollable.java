package fi.salminen.tomy.peak.network.api;


import rx.Observable;
import rx.Observer;

public abstract class BasePollable<T> implements Pollable, Observer {
    protected T api;
    private Observable observable; // TODO

    public BasePollable(T api) {
        this.api= api;
    }

    @Override
    public void subscribe(Observer observer) {
        observable.subscribe(observer);
    }

    public void renameMe(Observable observable) {
        observable.subscribe(this);
    }

    @Override
    public void onNext(Object o) {
        // TODO
    }

    @Override
    public void onCompleted() {
        // TODO
    }

    @Override
    public void onError(Throwable e) {
        // TODO
    }
}
