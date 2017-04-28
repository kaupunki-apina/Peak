package fi.salminen.tomy.peak.persistence.models;


import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;

public abstract class BaseViewModel<TModel extends BaseRXModel> {
    private TModel mModel;
    private boolean isBound = false;
    protected Context context;

    public BaseViewModel(Context context) {
        this.context = context;
    }

    @CallSuper
    public void bind(@NonNull TModel model) {
        isBound = true;
        if (model == null) {
            this.mModel = model;
            onBind();
        } else {
            onRebind(model);
            this.mModel = model;
        }
    }

    public void unbind() {
        onUnbind();
        isBound = false;
        this.mModel = null;
    }

    public boolean isBound() {
        return isBound;
    }

    protected TModel model() {
        return mModel;
    }

    /**
     * Binds ViewModel to a Model.
     *
     * Called if ViewModel is bound for the first time, or if rebinding after unbind()
     * has been called. ViewModel should prep itself for presenting.
     */
    protected abstract void onBind();

    /**
     * Unbinds ViewModel from a Model.
     *
     * ViewModel should release all resources.
     */
    protected abstract void onUnbind();

    /**
     * Binds ViewModel to a new Model.
     *
     * Called when bind() was called while still being bound to a Model. Gives a chance
     * for ViewModel to recycle resources. Note that model() will still return the
     * previously bound model.
     *
     * @param newModel Model to bind to
     */
    protected abstract void onRebind(@NonNull TModel newModel);
}
