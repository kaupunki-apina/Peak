package fi.salminen.tomy.peak.network.api;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.BuildConfig;


@Module
public class ConfigModule {

    public static final String API_URL_BASE = "ConfigModule.API_URL";

    @Provides
    @Named(ConfigModule.API_URL_BASE)
    String provideApiUrl() {
        return BuildConfig.URL_API_BASE;
    }
}
