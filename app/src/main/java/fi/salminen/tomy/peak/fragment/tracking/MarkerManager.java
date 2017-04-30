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


// TODO
// Listen for changes in the selected lines
public class MarkerManager {

    private Context context;
    private GoogleMap map;
    private FlowContentObserver fco;
    private BusViewModelPool mBusPool;

    public MarkerManager(Context context, GoogleMap map, FlowContentObserver fco) {
        this.context = context;
        this.map = map;
        this.fco = fco;
        this.mBusPool = new BusViewModelPool(context, map);
        initMarkers();
    }

    private void initMarkers() {
        RXSQLite.rx(getBusSql())
                .queryList()
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(this::observeChanges)
                .subscribe(busModels -> mBusPool.setData(busModels));
    }

    private void observeChanges() {
        fco.registerForContentChanges(context, BusModel.class);
        fco.addOnTableChangedListener((tableChanged, action) -> RXSQLite.rx(getBusSql())
                .queryList()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(busModels -> mBusPool.setData(busModels)));
    }

    public void dispose() {
        fco.unregisterForContentChanges(context);
        mBusPool.dispose();
        context = null;
        map = null;
    }

    private Where<BusModel> getBusSql() {
        return SQLite.select()
                .from(BusModel.class)
                // TODO Filter to selected lines only.
                .where(BusModel_Table.validUntilTime.greaterThan(new Date()));
    }
}
