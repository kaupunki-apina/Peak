package fi.salminen.tomy.peak.util.pool;


import android.content.Context;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import fi.salminen.tomy.peak.persistence.models.BaseViewModel;

public abstract class ViewModelPool<TViewModel extends BaseViewModel, TModel extends BaseRXModel> {
    private static final int DEFAULT_POOL_SIZE = 20;
    private ConcurrentLinkedQueue<TViewModel> unbound;
    private LinkedList<TViewModel> bound;
    private Context context;


    public ViewModelPool(@NonNull Context context) {
        this.context = context;
        unbound = new ConcurrentLinkedQueue<>();
        bound = new LinkedList<>();
    }

    public void setData(List<TModel> models) {
        TViewModel vm;

        // Recycle currently bound ViewModels
        for (int i = 0; i < models.size() && i < bound.size(); i++) {
            replace(i, models.get(i));
        }

        // Remove excess bound ViewModels, if the number of bound
        // ViewModels exceed the number of Models.
        for (int i = models.size(); i < bound.size(); i++) {
            vm = bound.remove(i);
            vm.unbind();
            unbound.add(vm);
        }

        // Recycle unbound ViewModels, if the number of Models
        // exceeds the number of ViewModels.
        for (int i = bound.size(); i < models.size(); i++) {
            add(models.get(i));
        }
    }

    public void replace(int index, TModel model) {
        if (index < bound.size()) {
            bound.get(index).bind(model);
        }
    }

    public void add(@NonNull TModel model) {
        TViewModel vm = unbound.poll();

        if (vm == null) {
            vm = newViewModel(context);
        }

        vm.bind(model);
        bound.add(vm);
    }

    public void dispose() {

    }

    abstract TViewModel newViewModel(Context context);
}
