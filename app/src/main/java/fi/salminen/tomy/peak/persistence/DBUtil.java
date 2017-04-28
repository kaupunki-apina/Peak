package fi.salminen.tomy.peak.persistence;


import android.support.annotation.NonNull;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class DBUtil {

    // TODO Attempt to change Subjects into Observables
    private PublishSubject<List<? extends BaseRXModel>> mSaveSubject = PublishSubject.create();
    private PublishSubject<Throwable> mErrorSubject = PublishSubject.create();
    private FlowContentObserver mFco;
    private static final String TAG = DBUtil.class.getName();

    public DBUtil(FlowContentObserver mFco) {
        this.mFco = mFco;
    }

    public void save(@NonNull List<? extends BaseRXModel> models) {
        mFco.beginTransaction();
        // Note: Switch to using FastStoreModelTransaction if performance becomes an issue.
        FlowManager.getDatabase(PeakDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<BaseRXModel>((model, wrapper) -> model.save(wrapper).subscribe())
                    .addAll(models)
                    .build())
                .error((Transaction transaction, Throwable error) -> {
                    Log.d(TAG, "Saving models erred:" + error.getMessage());
                    mFco.endTransactionAndNotify();
                    mErrorSubject.onError(error);
                })
                .success((Transaction transaction) -> {
                    mFco.endTransactionAndNotify();
                    mSaveSubject.onNext(models);
                })
                .build()
                .execute();
    }

    public Observable<Throwable> getErrorObservable() {
        return (Observable) mErrorSubject;
    }

    public Observable<List<? extends BaseModel>> getOnSaveObservable() {
        return (Observable) mSaveSubject;
    }
}
