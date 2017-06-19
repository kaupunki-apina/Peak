package fi.salminen.tomy.peak.network.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.persistence.models.BusModel;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ConfigModule.class})
public class JourneysApiModule {

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(BusModel.class, new BusModel.Deserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new StripResponse())
                .build();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient client, @Named(ConfigModule.API_URL_BASE) String url) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .client(client)
                .build();
    }

    @Provides
    JourneysApi provideJourneysApi(Retrofit retrofit) {
        return retrofit.create(JourneysApi.class);
    }
}
