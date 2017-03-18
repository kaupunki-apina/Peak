package fi.salminen.tomy.peak.network.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class JourneysApiModule {

    // TODO Determine what is the best way to assign this.
    private String mBaseUrl = "";

    @Provides
    @JourneysApiScope
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    @JourneysApiScope
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @JourneysApiScope
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(client)
                .build();
    }

    @Provides
    @JourneysApiScope
    Object provideBusApi(Retrofit retrofit) {
        // TODO
        return null;
    }

    @Provides
    @JourneysApiScope
    Object providesStopApi(Retrofit retrofit) {
        // TODO
        return null;
    }

    BusPollable providesBusPollable(@Named() Object tmp) {
        // TODO
        return null;
    }
}
