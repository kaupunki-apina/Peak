package fi.salminen.tomy.peak.fragment.tracking;


import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.rx2.language.RXSQLite;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.Date;

import fi.salminen.tomy.peak.persistence.models.bus.BusModel;
import fi.salminen.tomy.peak.persistence.models.bus.BusModel_Table;
import fi.salminen.tomy.peak.util.pool.BusViewModelPool;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;


// TODO
// Listen for changes in the selected lines
public class MarkerManager {

    private Context context;
    private FlowContentObserver fco;
    private BusViewModelPool mBusPool;
    private boolean isFcoRegistered;

    public MarkerManager(Context context, FlowContentObserver fco) {
        this.context = context;
        this.fco = fco;
        this.isFcoRegistered = false;
    }

    public void manage(GoogleMap map) {
        if (mBusPool == null) mBusPool = new BusViewModelPool(context, map);
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
                        .subscribe(busModels -> mBusPool.setData(busModels)));
            }

            isFcoRegistered = true;
        };

        RXSQLite.rx(getBusSql())
                .queryList()
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(registerFco)
                .subscribe(busModels -> mBusPool.setData(busModels));
    }

    public void dispose() {
        fco.unregisterForContentChanges(context);
        mBusPool.dispose();
        context = null;
    }

    private Where<BusModel> getBusSql() {
        return SQLite.select()
                .from(BusModel.class)
                // TODO Filter to selected lines only.
                .where(BusModel_Table.validUntilTime.greaterThan(new Date()));
    }
}
