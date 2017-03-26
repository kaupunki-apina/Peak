package fi.salminen.tomy.peak.persistence.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Bus {
    @Expose
    String vehicleRef;
    @Expose
    double longitude;
    @Expose
    double latitude;
    @Expose
    double bearing;
    @Expose
    String journeyPatternRef;
    @Expose
    float speed;
    @Expose
    Date validUntilTime;

    public static class Deserializer implements JsonDeserializer<Bus> {
        // TODO Unhardcore date format.
        private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        @Override
        public Bus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject monitoredVehicleJourney = json.getAsJsonObject().get("monitoredVehicleJourney").getAsJsonObject();
            try {
                Bus bus = new Bus();
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