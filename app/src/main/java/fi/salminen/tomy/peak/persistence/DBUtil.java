package fi.salminen.tomy.peak.persistence;


import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class DBUtil {

    // TODO Attempt to change Subjects into Observables
    private PublishSubject<List<? extends BaseModel>> mSaveSubject = PublishSubject.create();
    private PublishSubject<Throwable> mErrorSubject = PublishSubject.create();

    public void save(List<? extends BaseModel> models) {
        FlowManager.getDatabase(PeakDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(this::saveModel)
                        .addAll(models)
                        .build())
                .error((Transaction transaction, Throwable error) -> mErrorSubject.onError(error))
                .success((Transaction transaction) -> mSaveSubject.onNext(models))
                .build()
                .execute();
    }

    private void saveModel(BaseModel model, DatabaseWrapper wrapper) {
        model.save();
    }

    public Observable<Throwable> getErrorObservable() {
        return (Observable) mErrorSubject;
    }

    public Observable<List<? extends BaseModel>> getOnSaveObservable() {
        return (Observable) mSaveSubject;
    }
}
