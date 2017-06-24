package fi.salminen.tomy.peak.feature.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.network.api.ConfigModule;
import fi.salminen.tomy.peak.network.api.JourneysApi;
import fi.salminen.tomy.peak.network.api.JourneysApiModule;
import fi.salminen.tomy.peak.persistence.models.LineModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SelectLinesDialog extends DialogPreference {

    @BindView(R.id.dialog_select_lines_recyclerView)
    RecyclerView selectedLines;

    @BindView(R.id.dialog_select_lines_progressBar)
    ProgressBar loadingIcon;

    @Inject
    JourneysApi mApi;

    private SelectLinesDialogComponent component;
    private RecyclerViewAdapter mAdapter;

    public SelectLinesDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialog_select_lines);
        component = DaggerSelectLinesDialogComponent.builder()
                .configModule(new ConfigModule())
                .journeysApiModule(new JourneysApiModule())
                .build();

        component.inject(this);
    }

    @Override
    protected View onCreateDialogView() {
        View root = super.onCreateDialogView();
        this.mAdapter = new RecyclerViewAdapter();
        ButterKnife.bind(this, root);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        selectedLines.setAdapter(mAdapter);
        selectedLines.setLayoutManager(mLayoutManager);

        mApi.getLines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lineModels -> {
                    mAdapter.setLines(toViewStates(lineModels));
                    selectedLines.setVisibility(View.VISIBLE);
                    loadingIcon.setVisibility(View.GONE);
                });

        return root;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            // TODO Better handling. Storebox maybe?
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            String key = getContext().getResources().getString(R.string.prefs_select_lines_key);

            // Create and populate a new HashSet which contains all the selected
            // lines that are to be displayed on the map.
            HashSet<String> values = new HashSet<>();
            List<RecyclerViewAdapter.LineViewState> states = mAdapter.getViewStates();

            for (RecyclerViewAdapter.LineViewState state : states) {
                if (state.isChecked()) values.add(state.getId());
            }
            editor.putStringSet(key, values);
            editor.commit();
        }
    }

    private boolean isSelected(String lineId) {
        // TODO Better handling. Storebox maybe?
        String key = getContext().getResources().getString(R.string.prefs_select_lines_key);
        SharedPreferences prefs = getSharedPreferences();
        Set<String> selectedLines = prefs.getStringSet(key, null);

        // No saved preferences, default to all being shown.
        if (selectedLines == null) return true;

        Iterator<String> iterator = selectedLines.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().equals(lineId)) return true;
        }

        return false;
    }

    private List<RecyclerViewAdapter.LineViewState> toViewStates(List<LineModel> lineModels) {
        List<RecyclerViewAdapter.LineViewState> lines = new LinkedList<>();

        for (LineModel lineModel : lineModels) {
            lines.add(mAdapter.new LineViewState(
                    lineModel.name,
                    lineModel.name,
                    isSelected(lineModel.name)));
        }

        return lines;
    }
}
