package fi.salminen.tomy.peak.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.runtime.ContentResolverNotifier;

import fi.salminen.tomy.peak.core.BaseApplication;
import fi.salminen.tomy.peak.inject.application.BaseApplicationModule;
import fi.salminen.tomy.peak.network.api.JourneysApiModule;
import fi.salminen.tomy.peak.persistence.PeakDatabase;
import fi.salminen.tomy.peak.persistence.PersistenceModule;

import static fi.salminen.tomy.peak.config.Constants.Common.CONTENT_PROVIDER_AUTHORITY;


public class PeakApplication extends BaseApplication<PeakApplicationComponent> {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this)
                .addDatabaseConfig(DatabaseConfig.builder(PeakDatabase.class)
                        .modelNotifier(new ContentResolverNotifier(CONTENT_PROVIDER_AUTHORITY))
                        .build())
                .build());
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
                .persistenceModule(new PersistenceModule(this))
                .build();
    }

    public static PeakApplication getApplication(Context context) {
        return (PeakApplication) context.getApplicationContext();
    }
}
