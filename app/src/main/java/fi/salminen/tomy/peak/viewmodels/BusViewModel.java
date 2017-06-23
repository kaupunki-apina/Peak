package fi.salminen.tomy.peak.viewmodels;


import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fi.salminen.tomy.peak.persistence.models.BusModel;


public class BusViewModel extends BaseViewModel<BusModel> {

    private GoogleMap map;
    private Marker marker;

    public BusViewModel setMap(GoogleMap map) {
        this.map = map;
        return this;
    }

    @Override
    public void onBindModel() {
        marker = map.addMarker(
                new MarkerOptions()
                        .anchor(0.5f, 0.5f)
                        .position(latLngFromModel(model())));

        update(marker, model());
    }

    @Override
    protected void onUnbindModel() {
        if (marker != null) {
            marker.remove();
            marker = null;
        }
    }

    @Override
    protected void onRebindModel(@NonNull BusModel newModel) {
        update(marker, newModel);
    }

    private void update(Marker marker, BusModel model) {
        marker.setPosition(latLngFromModel(model));
        marker.setIcon(model.icon);
        marker.setRotation((float) model.bearing);

        Object tag = marker.getTag();

        if (tag == null) {
            marker.setTag(new BusMarkerTag(model.journeyPatternRef));
        } else {
            ((BusMarkerTag) tag).setJourneyPatternRef(model.journeyPatternRef);
        }
    }

    private LatLng latLngFromModel(BusModel model) {
        return new LatLng(model.latitude, model.longitude);
    }

    public class BusMarkerTag implements MarkerTag {
        private String journeyPatternRef;

        BusMarkerTag(String journeyPatternRef) {
            this.journeyPatternRef = journeyPatternRef;
        }

        void setJourneyPatternRef(String journeyPatternRef) {
            this.journeyPatternRef = journeyPatternRef;
        }

        @Override
        public String getInfoText() {
            return journeyPatternRef;
        }
    }
}
