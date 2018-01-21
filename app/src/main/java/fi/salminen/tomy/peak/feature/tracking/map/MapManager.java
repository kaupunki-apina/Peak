package fi.salminen.tomy.peak.feature.tracking.map;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.Date;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.persistence.PeakPrefs;
import fi.salminen.tomy.peak.persistence.models.BusModel;
import fi.salminen.tomy.peak.persistence.models.BusModel_Table;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.subjects.BehaviorSubject;

public abstract class MapManager<MapView extends View> {
    private Unbinder mUnbinder;
    private FlowContentObserver fco;
    private Context context;
    private PeakPrefs prefs;
    private BehaviorSubject<List<BusModel>> busSubject = BehaviorSubject.create();

    @BindView(R.id.mapView)
    MapView mapView;

    /**
     * Constructs a new MapManager instance.
     * <p>
     * Subclass should leverage Dagger 2's constructor injection to
     * acquire required instances of FlowContentObserver and PeakPrefs
     * and then pass them on to super class.
     */
    public MapManager(FlowContentObserver fco, PeakPrefs prefs, Context context) {
        this.prefs = prefs;
        this.context = context;
        this.fco = fco;
    }


    protected Observable<List<BusModel>> getBusSubject() {
        return busSubject;
    }

    protected MapView getMapView() {
        return this.mapView;
    }

    private Where<BusModel> getBusQuery() {
        Where<BusModel> sql = SQLite.select()
                .from(BusModel.class)
                .where(BusModel_Table.validUntilTime.greaterThan(new Date()));

        Set<String> selectedLines = prefs.getSelectedLines();
        if (selectedLines != null) {
            return sql.and(BusModel_Table.journeyPatternRef.in(selectedLines));
        }

        return sql;
    }

    /**
     * Fragment lifecycle methods, should MapView need them.
     */

    @CallSuper
    public void onCreateView(View root) {
        mUnbinder = ButterKnife.bind(this, root);
    }

    public void onStart() {


        Action registerFco = () -> {
            fco.registerForContentChanges(context, BusModel.class);
            fco.addOnTableChangedListener((tableChanged, action) -> RXSQLite.rx(getBusQuery())
                    .queryList()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(buses -> busSubject.onNext(buses)));
        };

        RXSQLite.rx(getBusQuery())
                .queryList()
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(registerFco)
                .subscribe(buses -> busSubject.onNext(buses));
    }

    public void onResume() {
    }

    public void onPause() {
    }

    @CallSuper
    public void onStop() {
        fco.unregisterForContentChanges(context);
    }

    public void onLowMemory() {
    }

    @CallSuper
    public void onDestroyView() {
        mUnbinder.unbind();
    }

    public void onDestroy() {
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void onSaveInstanceState(Bundle outState) {
    }
}
