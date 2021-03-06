package fi.salminen.tomy.peak.util.pool;


import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import fi.salminen.tomy.peak.viewmodels.BaseViewModel;

public abstract class ViewModelPool<TViewModel extends BaseViewModel, TModel extends BaseRXModel> {
    private ConcurrentLinkedQueue<TViewModel> unbound;
    private LinkedList<TViewModel> bound;


    public ViewModelPool() {
        unbound = new ConcurrentLinkedQueue<>();
        bound = new LinkedList<>();
    }

    /**
     * Replaces existing data set with the given data set.
     *
     * @param models Models to rebind existing data set.
     */
    public void setData(List<TModel> models) {
        TViewModel vm;

        // Recycle currently bound ViewModels
        for (int i = 0; i < models.size() && i < bound.size(); i++) {
            rebind(i, models.get(i));
        }

        // Remove excess bound ViewModels, if the number of bound
        // ViewModels exceed the number of Models.
        for (int i = bound.size() - 1; i >= models.size(); i--) {
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

    /**
     * Rebind ViewModel at index to the given model.
     *
     * @param index Index of the ViewModel to rebind.
     * @param model Model to bind to.
     */
    private void rebind(int index, TModel model) {
        if (index < bound.size()) {
            bound.get(index).bind(model);
        }
    }

    /**
     * Appends the model to the end of current data set.
     *
     * @param model Model to append
     */
    public void add(@NonNull TModel model) {
        TViewModel vm = unbound.poll();

        if (vm == null) {
            vm = newViewModel();
        }

        vm.bind(model);
        bound.add(vm);
    }

    /**
     * Dispose of assets rendering the instance unusable.
     *
     * Use to prevent things such as Context from leaking.
     */
    public void dispose() {
        for (TViewModel viewModel : bound) {
            viewModel.unbind();
            viewModel.dispose();
        }
    }

    public List<TViewModel> getViewModels() {
        return bound;
    }

    abstract TViewModel newViewModel();
}
