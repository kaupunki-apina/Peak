package fi.salminen.tomy.peak.core;


import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import fi.salminen.tomy.peak.inject.Injector;

public abstract class BaseFragment<T> extends Fragment implements Injector<T> {

    private T component;

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
