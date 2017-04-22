package fi.salminen.tomy.peak.core;

import android.app.Service;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import fi.salminen.tomy.peak.inject.Injector;


public abstract class BaseService<T> extends Service implements Injector<T> {

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
