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

@Module(includes = {ConfigModule.class})
public class JourneysApiModule {

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    Retrofit provideRetrofit(
            Gson gson,
            OkHttpClient client,
            @Named(ConfigModule.API_URL_BASE) String url) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .client(client)
                .build();
    }

    @Provides
    JourneysApi provideJourneysApi(Retrofit retrofit) {
        return retrofit.create(JourneysApi.class);
    }
}
