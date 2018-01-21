package fi.salminen.tomy.peak.core;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import fi.salminen.tomy.peak.inject.Injector;

public abstract class BaseFragment<T> extends Fragment implements Injector<T> {

    private T component;

    @SuppressWarnings("deprecation")
    @CallSuper
    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Perform injection here before M, L (API 22) and below because onAttach(Context)
            // is not yet available at L.
            inject();
        }
        super.onAttach(activity);
    }

    @CallSuper
    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Perform injection here for M (API 23) due to deprecation of onAttach(Activity).
            inject();
        }
        super.onAttach(context);
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
