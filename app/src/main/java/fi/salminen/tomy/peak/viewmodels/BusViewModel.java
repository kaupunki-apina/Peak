package fi.salminen.tomy.peak.viewmodels;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fi.salminen.tomy.peak.persistence.models.BusModel;

// TODO
// - Open dialog on click
public class BusViewModel extends BaseViewModel<BusModel> {

    private GoogleMap map;
    private Marker marker;


    public BusViewModel(Context context, GoogleMap map) {
        super(context);
        this.map = map;
    }

    @Override
    public void onBindModel() {
        marker = map.addMarker(
                new MarkerOptions()
                        .position(latLngFromModel(model())));
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
    }

    private LatLng latLngFromModel(BusModel model) {
        return new LatLng(model.latitude, model.longitude);
    }
}
