package fi.salminen.tomy.peak.models;

import com.google.gson.annotations.Expose;

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
}