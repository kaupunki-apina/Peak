package fi.salminen.tomy.peak.persistence.models.bus;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fi.salminen.tomy.peak.persistence.PeakDatabase;


@Table(database = PeakDatabase.class)
public class BusModel extends BaseRXModel {
    @Column
    @PrimaryKey
    @Expose
    String vehicleRef;
    @Column
    @Expose
    double longitude;
    @Column
    @Expose
    double latitude;
    @Column
    @Expose
    double bearing;
    @Column
    @Expose
    String journeyPatternRef;
    @Column
    @Expose
    float speed;
    @Column
    @Expose
    Date validUntilTime;

    public static class Deserializer implements JsonDeserializer<BusModel> {
        // TODO Unhardcore date format.
        private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        @Override
        public BusModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject monitoredVehicleJourney = json.getAsJsonObject().get("monitoredVehicleJourney").getAsJsonObject();
            try {
                BusModel bus = new BusModel();
                bus.bearing = monitoredVehicleJourney.get("bearing").getAsDouble();
                bus.validUntilTime = formatter.parse(json.getAsJsonObject().get("validUntilTime").getAsString());
                bus.vehicleRef = monitoredVehicleJourney.get("vehicleRef").getAsString();
                bus.longitude = monitoredVehicleJourney.get("vehicleLocation").getAsJsonObject().get("longitude").getAsDouble();
                bus.latitude = monitoredVehicleJourney.get("vehicleLocation").getAsJsonObject().get("latitude").getAsDouble();
                bus.journeyPatternRef = monitoredVehicleJourney.get("journeyPatternRef").getAsString();
                bus.speed = monitoredVehicleJourney.get("speed").getAsFloat();
                return bus;
            } catch (ParseException e) {
                e.printStackTrace();
                throw new JsonParseException(e.getMessage());
            }
        }
    }

}