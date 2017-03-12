package fi.salminen.tomy.peak.core;


import android.app.Application;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import fi.salminen.tomy.peak.inject.Injector;


public abstract class BaseApplication<T> extends Application implements Injector<T> {

    private T component;

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    @NonNull
    @Override
    public T component() {
        if (component == null) {
            component = createComponent();
        }
        return component;
    }

    @NonNull
    protected abstract T createComponent();
}
