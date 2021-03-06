package fi.salminen.tomy.peak.persistence.models;

import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
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

import fi.salminen.tomy.peak.config.Constants;
import fi.salminen.tomy.peak.persistence.PeakDatabase;


@Table(database = PeakDatabase.class)
public class BusModel extends BaseRXModel {
    @Column
    @PrimaryKey
    @Expose
    public String vehicleRef;
    @Column
    @Expose
    public double longitude;
    @Column
    @Expose
    public double latitude;
    @Column
    @Expose
    public double bearing;
    @Column
    @Expose
    public String journeyPatternRef;
    @Column
    @Expose
    public float speed;
    @Column
    @Expose
    public Date validUntilTime;

    // It's a bad idea to have representation data tied to
    // the model itself, but it's incredibly easy to pass around this way ¯\_(ツ)_/¯
    public BitmapDescriptor icon;

    public static class Deserializer implements JsonDeserializer<BusModel> {
        private SimpleDateFormat formatter = new SimpleDateFormat(Constants.API.DATE_FORMAT);
        private static final String TAG = Deserializer.class.getName();

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
                Log.d(TAG, "Json parsing failed: " + e.getMessage());
                throw new JsonParseException(e.getMessage());
            }
        }
    }
}