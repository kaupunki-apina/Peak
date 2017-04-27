package fi.salminen.tomy.peak.persistence;


import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Singleton
@Module
public class PersistenceModule {

    @Provides
    @Singleton
    FlowContentObserver provideFlowContentObserver() {
        FlowContentObserver fco = new FlowContentObserver();
        fco.setNotifyAllUris(false);
        return fco;
    }

    @Provides
    @Singleton
    DBUtil provideDBUtil(FlowContentObserver fco) {
        return new DBUtil(fco);
    }
}
