package fi.salminen.tomy.peak.util;


import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.Date;
import java.util.List;

import fi.salminen.tomy.peak.persistence.models.BusModel;
import fi.salminen.tomy.peak.persistence.models.BusModel_Table;
import fi.salminen.tomy.peak.util.icons.IconFactory;
import fi.salminen.tomy.peak.util.pool.BusViewModelPool;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


// TODO
// Listen for changes in the selected lines
public class MarkerManager {
    private IconFactory iconFactory;
    private Context context;
    private FlowContentObserver fco;
    private BusViewModelPool mBusPool;
    private boolean isFcoRegistered;

    public MarkerManager(Context context, FlowContentObserver fco, IconFactory iconFactory) {
        this.iconFactory = iconFactory;
        this.context = context;
        this.fco = fco;
        this.isFcoRegistered = false;
    }

    public void manage(GoogleMap map) {
        if (mBusPool == null) mBusPool = new BusViewModelPool(map);
        initMarkers();
    }

    private void initMarkers() {
        // Registers a flow content observer which listens for
        // changes in BusModel table.
        Action registerFco = () -> {
            if (!isFcoRegistered) {
                fco.registerForContentChanges(context, BusModel.class);
                fco.addOnTableChangedListener((tableChanged, action) -> RXSQLite.rx(getBusSql())
                        .queryList()
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::generateIconsAndUpdate));
            }

            isFcoRegistered = true;
        };

        RXSQLite.rx(getBusSql())
                .queryList()
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(registerFco)
                .subscribe(this::generateIconsAndUpdate);
    }

    public void dispose() {
        fco.unregisterForContentChanges(context);
        mBusPool.dispose();
        context = null;
    }

    private Disposable generateIconsAndUpdate(List<BusModel> busModels) {
        return iconFactory.getBusIcon(busModels)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> mBusPool.setData(busModels))
                .subscribe();
    }

    private Where<BusModel> getBusSql() {
        return SQLite.select()
                .from(BusModel.class)
                // TODO Filter to selected lines only.
                .where(BusModel_Table.validUntilTime.greaterThan(new Date()));
    }
}
