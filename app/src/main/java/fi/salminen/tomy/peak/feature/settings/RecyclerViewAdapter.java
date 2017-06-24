package fi.salminen.tomy.peak.feature.settings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fi.salminen.tomy.peak.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private List<LineViewState> viewStates;

    RecyclerViewAdapter() {
        this.viewStates = new ArrayList<>();
    }

    void setLines(List<LineViewState> viewStates) {
        this.viewStates = viewStates;
        notifyDataSetChanged();
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


    class LineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_select_line_label)
        TextView lineRef;

        @BindView(R.id.list_item_select_line_is_checked)
        CheckBox isChecked;

        private int position;

        LineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void display(int position) {
            this.position = position;
            LineViewState lineItem = RecyclerViewAdapter.this.viewStates.get(position);
            lineRef.setText(lineItem.getLabel());
            isChecked.setChecked(lineItem.isChecked());
        }

        @OnClick(R.id.list_item_select_line_root)
        void toggle() {
            RecyclerViewAdapter.this.viewStates.get(position).toggle();
            isChecked.setChecked(!isChecked.isChecked());
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
