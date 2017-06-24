package fi.salminen.tomy.peak.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import fi.salminen.tomy.peak.core.BaseApplication;
import fi.salminen.tomy.peak.inject.application.BaseApplicationModule;
import fi.salminen.tomy.peak.network.api.JourneysApiModule;
import fi.salminen.tomy.peak.persistence.PersistenceModule;


public class PeakApplication extends BaseApplication<PeakApplicationComponent> {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());

    }

    @Override
    public void inject() {
        component().inject(this);
    }

    @NonNull
    @Override
    protected PeakApplicationComponent createComponent() {
        return DaggerPeakApplicationComponent.builder()
                .baseApplicationModule(new BaseApplicationModule(this))
                .journeysApiModule(new JourneysApiModule())
                .persistenceModule(new PersistenceModule(PeakApplication.getApplication(this)))
                .build();
    }

    public static PeakApplication getApplication(Context context) {
        return (PeakApplication) context.getApplicationContext();
    }
}
