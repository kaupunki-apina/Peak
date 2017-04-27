package fi.salminen.tomy.peak.persistence.models;


import android.content.Context;

import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;

public abstract class BaseViewModel<TModel extends BaseRXModel> {
    protected TModel mModel;
    protected Context context;

    public BaseViewModel(TModel model, Context context) {
        this.mModel = model;
        this.context = context;
    }
}
