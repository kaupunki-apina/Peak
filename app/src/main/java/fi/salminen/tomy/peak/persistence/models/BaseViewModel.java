package fi.salminen.tomy.peak.persistence.models;


import android.content.Context;

import com.raizlabs.android.dbflow.structure.BaseModel;

public abstract class BaseViewModel<T extends BaseModel> {
    protected T mModel;
    protected Context context;

    public BaseViewModel(T model, Context context) {
        this.mModel = model;
        this.context = context;
    }
}
