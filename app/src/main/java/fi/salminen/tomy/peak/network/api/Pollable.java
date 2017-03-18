package fi.salminen.tomy.peak.network.api;


import rx.Observer;

public interface Pollable<T> {
    void poll();
    void subscribe(Observer<T> observer);
}
