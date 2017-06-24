package fi.salminen.tomy.peak.feature.settings;

import android.content.Context;
import android.preference.DialogPreference;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.network.api.ConfigModule;
import fi.salminen.tomy.peak.network.api.JourneysApi;
import fi.salminen.tomy.peak.network.api.JourneysApiModule;
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
                    mAdapter.setItems(lineModels);
                    selectedLines.setVisibility(View.VISIBLE);
                    loadingIcon.setVisibility(View.GONE);
                });

        return root;
    }
}
