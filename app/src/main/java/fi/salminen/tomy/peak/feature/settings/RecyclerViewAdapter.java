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
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.persistence.models.LineModel;


public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private List<ListItem> items;

    RecyclerViewAdapter() {
        this.items = new ArrayList<>();
    }


    void setItems(List<LineModel> lines) {
        items.clear();

        for (LineModel line : lines) {
            items.add(new ListItem(line));
        }

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_select_line, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).display(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_select_line_label)
        TextView lineRef;

        @BindView(R.id.list_item_select_line_is_checked)
        CheckBox isChecked;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void display(int position) {
            ListItem listItem = RecyclerViewAdapter.this.items.get(position);
            lineRef.setText(listItem.getLabel());
            isChecked.setChecked(listItem.isSelected());
        }
    }

    private class ListItem {
        LineModel lineModel;

        ListItem(LineModel lineModel) {
            this.lineModel = lineModel;
        }

        String getLabel() {
            return lineModel.name;
        }

        boolean isSelected() {
            // TODO
            return true;
        }
    }
}
