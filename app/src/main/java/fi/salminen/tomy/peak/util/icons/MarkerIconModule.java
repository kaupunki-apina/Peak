package fi.salminen.tomy.peak.util.icons;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import dagger.Module;
import dagger.Provides;
import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.inject.application.ForApplication;


@Module
public class MarkerIconModule {

    private BitmapDescriptor bitmapDescriptorFromLayout(Context context, int layout) {
        int px = context.getResources().getDimensionPixelSize(R.dimen.bus_icon_side);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layout, null);

        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.layout(0, 0, px, px);
        view.buildDrawingCache();
        Bitmap bm = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        view.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bm);
    }

    @Provides
    @BusIconStationary
    BitmapDescriptor provideBusIconStationary(@ForApplication Context context) {
        return bitmapDescriptorFromLayout(context, R.layout.bus_icon_stationary);
    }

    @Provides
    @BusIconMoving
    BitmapDescriptor provideBusIconMoving(@ForApplication Context context) {
        return bitmapDescriptorFromLayout(context, R.layout.bus_icon_moving);
    }
}
