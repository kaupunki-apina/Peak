package fi.salminen.tomy.peak.persistence;


import android.content.Context;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import net.orange_box.storebox.StoreBox;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static fi.salminen.tomy.peak.config.Constants.Common.CONTENT_PROVIDER_AUTHORITY;


@Module
public class PersistenceModule {
    private Context context;

    public PersistenceModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    FlowContentObserver provideFlowContentObserver() {
        FlowContentObserver fco = new FlowContentObserver(CONTENT_PROVIDER_AUTHORITY);
        fco.setNotifyAllUris(false);
        return fco;
    }

    @Provides
    @Singleton
    DBUtil provideDBUtil(FlowContentObserver fco) {
        return new DBUtil(fco);
    }

    @Provides
    PeakPrefs providePeakPrefs() {
        return StoreBox.create(context, PeakPrefs.class);
    }
}
