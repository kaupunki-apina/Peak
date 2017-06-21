package fi.salminen.tomy.peak.viewmodels;


import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import fi.salminen.tomy.peak.persistence.models.BusModel;

// TODO
// - Open dialog on click
public class BusViewModel extends BaseViewModel<BusModel> {

    private GoogleMap map;
    private Marker marker;
    private BitmapDescriptor iconMoving;
    private BitmapDescriptor iconStationary;

    @Inject
    public BusViewModel(BitmapDescriptor iconMoving, BitmapDescriptor iconStationary) {
        this.iconMoving = iconMoving;
        this.iconStationary = iconStationary;

    }

    public BusViewModel setMap(GoogleMap map) {
        this.map = map;
        return this;
    }

    @Override
    public void onBindModel() {
        marker = map.addMarker(
                new MarkerOptions()
                        .icon(getIcon(model()))
                        .position(latLngFromModel(model())));

        marker.setTag(new BusMarkerTag(model().journeyPatternRef));
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
        marker.setPosition(latLngFromModel(newModel));
        marker.setIcon(getIcon(newModel));
        ((BusMarkerTag) marker.getTag()).setLineNum(newModel.journeyPatternRef);
    }

    private LatLng latLngFromModel(BusModel model) {
        return new LatLng(model.latitude, model.longitude);
    }

    private BitmapDescriptor getIcon(BusModel model) {
        return model.speed == 0 ? iconStationary : iconMoving;
    }

    public class BusMarkerTag implements MarkerTag {
        private String lineNum;

        BusMarkerTag(String lineNum) {
            this.lineNum = lineNum;
        }

        void setLineNum(String lineNum) {
            this.lineNum = lineNum;
        }

        @Override
        public String getInfoText() {
            return lineNum;
        }
    }
}
