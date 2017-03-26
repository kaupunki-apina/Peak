package fi.salminen.tomy.peak.persistence;


import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;


public class DBUtil {

    public void save(List<? extends BaseModel> models) {
        FlowManager.getDatabase(PeakDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(this::saveModel)
                        .addAll(models)
                        .build())
                .error(this::onError)
                .success(this::onSuccess)
                .build()
                .execute();
    }

    private void onSuccess(Transaction transaction) {
        // TODO
    }

    private void onError(Transaction transaction, Throwable error) {
        // TODO
    }

    private void saveModel(BaseModel model, DatabaseWrapper wrapper) {
        // TODO Emit values to subs?
        model.save();
    }
}
