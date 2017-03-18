package fi.salminen.tomy.peak.network.api;

import java.util.List;

import fi.salminen.tomy.peak.models.Bus;
import retrofit2.http.GET;
import rx.Observable;

public interface BusJourneysApi {

    @GET("vehicle-activity?exclude-fields=monitoredVehicleJourney.onwardCalls,recordedAttTimes")
    Observable<List<Bus>> getBuses(); // TODO Endpoint urls
}
