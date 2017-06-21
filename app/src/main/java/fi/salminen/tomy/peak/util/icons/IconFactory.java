package fi.salminen.tomy.peak.util.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.persistence.models.BusModel;


public class IconFactory {

    private Context context;
    private TextView journeyPatternRefLabel;
    private View backgroundStationary;
    private View backgroundMoving;
    private int sideLength;
    private int halfLength;

    IconFactory(Context context, int resIdLabel, int resIdStationary, int resIdMoving) {
        this.context = context;
        this.journeyPatternRefLabel = (TextView)inflate(resIdLabel);
        this.backgroundStationary = inflate(resIdStationary);
        this.backgroundMoving = inflate(resIdMoving);
        this.sideLength = context.getResources().getDimensionPixelSize(R.dimen.bus_icon_side);
        this.halfLength = sideLength / 2;
    }

    /**
     * Inflates a view from layout resource file.
     *
     * @param resId Resource Id
     * @return Inflated view
     */
    private View inflate(int resId) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resId, null);

        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    /**
     * Creates an appropriate icon for the given BusModel.
     *
     * @param model Model for which an icon should be generated.
     * @return Ready to use icon.
     */
    public BitmapDescriptor getBusIcon(BusModel model) {
        // Icon for moving has a direction indicator.
        View background = model.speed == 0 ? backgroundStationary : backgroundMoving;

        // Create a canvas and rotate it to the direction of movement.
        Bitmap bm = Bitmap.createBitmap(sideLength, sideLength, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        canvas.rotate((float) model.bearing, halfLength, halfLength);

        // Draw background.
        background.layout(0, 0, sideLength, sideLength);
        background.buildDrawingCache();
        background.draw(canvas);
        background.destroyDrawingCache();

        // Rotate back so that the text is aligned correctly.
        canvas.rotate((float) -model.bearing, halfLength, halfLength);

        // Draw text on top.
        journeyPatternRefLabel.setText(model.journeyPatternRef);
        journeyPatternRefLabel.buildDrawingCache();
        journeyPatternRefLabel.layout(0, 0, sideLength, sideLength);
        journeyPatternRefLabel.draw(canvas);
        journeyPatternRefLabel.destroyDrawingCache();

        return BitmapDescriptorFactory.fromBitmap(bm);
    }
}
