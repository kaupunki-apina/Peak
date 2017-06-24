package fi.salminen.tomy.peak.network.api;

import java.util.List;

import fi.salminen.tomy.peak.persistence.models.BusModel;
import fi.salminen.tomy.peak.persistence.models.LineModel;
import io.reactivex.Observable;
import retrofit2.http.GET;


public interface JourneysApi {

    @GET("vehicle-activity?exclude-fields=monitoredVehicleJourney.onwardCalls,recordedAttTimes")
    Observable<List<BusModel>> getBuses();

    @GET("lines")
    Observable<List<LineModel>> getLines();
}
