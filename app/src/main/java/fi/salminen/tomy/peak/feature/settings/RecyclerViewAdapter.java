package fi.salminen.tomy.peak.feature.settings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.salminen.tomy.peak.R;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private List<LineViewState> viewStates;
    private int selectedCount;
    private PublishSubject<Boolean> onToggleSubject;

    RecyclerViewAdapter() {
        this.viewStates = new ArrayList<>();
        onToggleSubject = PublishSubject.create();
    }

    void setLines(List<LineViewState> viewStates) {
        this.selectedCount = 0;
        this.viewStates = viewStates;
        notifyDataSetChanged();

        for(LineViewState viewState : viewStates) {
            if (viewState.isChecked()) selectedCount++;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_select_line, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LineViewHolder) holder).display(position);
    }

    @Override
    public int getItemCount() {
        return viewStates.size();
    }

    public List<LineViewState> getViewStates() {
        return viewStates;
    }

    public Observable<Boolean> getOnToggleObservable() {
        return (Observable) onToggleSubject;
    }

    public boolean isAllSelected() {
        return this.selectedCount == this.viewStates.size();
    }


    class LineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_select_line_label)
        TextView lineRef;

        @BindView(R.id.list_item_select_line_is_checked)
        CheckBox checkBox;

        private int position;

        LineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void display(int position) {
            this.position = position;
            LineViewState lineItem = RecyclerViewAdapter.this.viewStates.get(position);
            lineRef.setText(lineItem.getLabel());
            checkBox.setChecked(lineItem.isChecked());
        }

        @OnClick(R.id.list_item_select_line_root)
        void toggle() {
            RecyclerViewAdapter.this.viewStates.get(position).toggle();
            checkBox.setChecked(!checkBox.isChecked());

            if (checkBox.isChecked()) {
                RecyclerViewAdapter.this.selectedCount++;
            } else {
                RecyclerViewAdapter.this.selectedCount--;
            }

            RecyclerViewAdapter.this.onToggleSubject.onNext(true);
        }
    }

    public void selectAll() {
        setAll(true);
        this.selectedCount = this.viewStates.size();
    }

    public void deselectAll() {
        setAll(false);
        this.selectedCount = 0;
    }

    private void setAll(boolean checked) {
        this.selectedCount = this.viewStates.size();
        ListIterator<LineViewState> it = this.viewStates.listIterator();
        int index;

        while(it.hasNext()) {
            index = it.nextIndex();
            if (it.next().setIsChecked(checked)) notifyItemChanged(index);
        }
    }

    class LineViewState {
        private String id;
        private String label;
        private boolean isChecked;

        LineViewState(String id, String label, boolean isChecked) {
            this.id = id;
            this.label = label;
            this.isChecked = isChecked;
        }

        private boolean setIsChecked(boolean isChecked) {
            boolean didChange = this.isChecked != isChecked;
            this.isChecked = isChecked;
            return didChange;
        }

        public String getLabel() {
            return label;
        }

        public String getId() {
            return id;
        }

        public boolean isChecked() {
            return isChecked;
        }

        void toggle() {
            isChecked = !isChecked;
        }
    }
}
