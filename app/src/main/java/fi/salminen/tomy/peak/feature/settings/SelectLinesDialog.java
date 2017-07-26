package fi.salminen.tomy.peak.feature.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.network.api.ConfigModule;
import fi.salminen.tomy.peak.network.api.JourneysApi;
import fi.salminen.tomy.peak.network.api.JourneysApiModule;
import fi.salminen.tomy.peak.persistence.DaggerPersistenceComponent;
import fi.salminen.tomy.peak.persistence.PeakPrefs;
import fi.salminen.tomy.peak.persistence.PersistenceComponent;
import fi.salminen.tomy.peak.persistence.PersistenceModule;
import fi.salminen.tomy.peak.persistence.models.LineModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SelectLinesDialog extends DialogPreference {
    private SelectLinesDialogComponent component;
    private RecyclerViewAdapter mAdapter;

    @BindView(R.id.dialog_select_lines_recyclerView)
    RecyclerView selectedLines;

    @BindView(R.id.dialog_select_lines_progressBar)
    ProgressBar loadingIcon;

    @BindString(R.string.prefs_select_lines_action_deselect_all)
    String actionDeselectAll;

    @BindString(R.string.prefs_select_lines_action_select_all)
    String actionSelectAll;

    @BindString(R.string.prefs_select_lines_summary_none)
    String summaryNone;

    @Inject
    JourneysApi mApi;

    @Inject
    PeakPrefs prefs;

    public SelectLinesDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialog_select_lines);

        PersistenceComponent pc = DaggerPersistenceComponent.builder()
                .persistenceModule(new PersistenceModule(context))
                .build();

        component = DaggerSelectLinesDialogComponent.builder()
                .configModule(new ConfigModule())
                .journeysApiModule(new JourneysApiModule())
                .persistenceComponent(pc)
                .build();

        component.inject(this);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        builder.setNeutralButton(actionDeselectAll, null);
    }

    @Override
    protected View onCreateDialogView() {
        View root = super.onCreateDialogView();
        this.mAdapter = new RecyclerViewAdapter();
        ButterKnife.bind(this, root);
        
        // updateSummary();

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
                    Button neutral = ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEUTRAL);
                    neutral.setText(mAdapter.isAllSelected() ? actionDeselectAll : actionSelectAll);
                });

        return root;
    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);
        final Button neutral = ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEUTRAL);
        neutral.setOnClickListener(v -> {
            if (mAdapter.isAllSelected()) {
                mAdapter.deselectAll();
                neutral.setText(actionSelectAll);
            } else {
                mAdapter.selectAll();
                neutral.setText(actionDeselectAll);
            }
        });

        // Update the label to correspond the action
        this.mAdapter.getOnToggleObservable().subscribe(aVoid -> {
            if (mAdapter.isAllSelected()) {
                neutral.setText(actionDeselectAll);
            } else {
                neutral.setText(actionSelectAll);
            }
        });
    }


    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            // Create and populate a new HashSet which contains all the selected
            // lines that are to be displayed on the map.
            HashSet<String> values = new HashSet<>();
            List<RecyclerViewAdapter.LineViewState> states = mAdapter.getViewStates();

            for (RecyclerViewAdapter.LineViewState state : states) {
                if (state.isChecked()) values.add(state.getId());
            }

            prefs.setSelectedLines(values);
            //updateSummary();
        }
    }

    private boolean isSelected(String lineId) {
        Set<String> selectedLines = prefs.getSelectedLines();

        // No saved preferences, default to all being shown.
        if (selectedLines == null) return true;

        for (String selectedLine : selectedLines) {
            if (selectedLine.equals(lineId)) return true;
        }

        return false;
    }
    /**
    private void updateSummary() {
        Set<String> values = prefs.getStringSet(getKey(), null);

        if (values == null) {
            // TODO Fetch available lines and display them
            setSummary("ALL");
        } else if (values.size() == 0) {
            setSummary(summaryNone);
        } else {
            // TODO Sorting
            List<String> list = new ArrayList<>(values);
            Iterator<String> it = list.iterator();
            StringBuilder sb = new StringBuilder();

            while (it.hasNext()) {
                sb.append(it.next());
                if (it.hasNext()) sb.append(',');
            }

            setSummary(sb.toString());
        }
    }

     **/

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
