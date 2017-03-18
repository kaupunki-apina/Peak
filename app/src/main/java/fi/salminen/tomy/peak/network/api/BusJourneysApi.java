package fi.salminen.tomy.peak.network.api;

import java.util.List;

import fi.salminen.tomy.peak.models.Bus;
import retrofit2.http.GET;
import rx.Observable;

public interface BusJourneysApi {

    @GET("TODO")
    Observable<List<Bus>> getBuses(); // TODO Endpoint urls
}
