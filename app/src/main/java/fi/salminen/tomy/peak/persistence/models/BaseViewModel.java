package fi.salminen.tomy.peak.persistence.models;


import android.content.Context;

import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;

public abstract class BaseViewModel<T extends BaseRXModel> {
    protected T mModel;
    protected Context context;

    public BaseViewModel(T model, Context context) {
        this.mModel = model;
        this.context = context;
    }
}
