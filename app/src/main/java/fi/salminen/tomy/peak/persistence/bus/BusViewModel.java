package fi.salminen.tomy.peak.persistence.bus;


import android.content.Context;

import fi.salminen.tomy.peak.persistence.BaseViewModel;

// TODO
// - Location from model fit for gMaps consumption
// - Open dialog on click
public class BusViewModel extends BaseViewModel<BusModel> {

    public BusViewModel(BusModel model, Context context) {
        super(model, context);
    }
}
