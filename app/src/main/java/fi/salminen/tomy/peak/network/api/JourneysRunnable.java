package fi.salminen.tomy.peak.network.api;

import android.support.annotation.NonNull;


public class JourneysRunnable implements Runnable {
    private Pollable mPollable;

    public JourneysRunnable(@NonNull Pollable pollable) {
        this.mPollable = pollable;
    }

    @Override
    public void run() {
        // TODO
        // Poll API and publish results via RxJava
    }
}
