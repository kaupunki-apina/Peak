package fi.salminen.tomy.peak.feature.tracking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.List;

import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.persistence.models.BusModel;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class IconFactory {
    private int resIdStationary;
    private int resIdMoving;
    private int resIdLabel;

    public IconFactory(int resIdLabel, int resIdStationary, int resIdMoving) {
        this.resIdLabel = resIdLabel;
        this.resIdStationary = resIdStationary;
        this.resIdMoving = resIdMoving;
    }

    /**
     * Inflates a view from layout resource file.
     *
     * @param resId Resource Id
     * @return Inflated view
     */
    private View inflate(Context context, int resId) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        View view = LayoutInflater.from(context).inflate(resId, null);

        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }


    public Observable<Void> getBusIcon(Context context, List<BusModel> models) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                // Views to draw onto canvas
                TextView journeyPatternRefLabel = (TextView) inflate(context, resIdLabel);
                View backgroundStationary = inflate(context, resIdStationary);
                View backgroundMoving = inflate(context, resIdMoving);

                // Marker dimensions
                int sideLength = context.getResources().getDimensionPixelSize(R.dimen.bus_icon_side);
                int halfLength = sideLength / 2;

                Rect rect = new Rect();
                rect.set(0, 0, sideLength, sideLength);

                int widthSpec = View.MeasureSpec.makeMeasureSpec(rect.width(), View.MeasureSpec.EXACTLY);
                int heightSpec = View.MeasureSpec.makeMeasureSpec(rect.height(), View.MeasureSpec.EXACTLY);


                for (BusModel model : models) {
                    // Icon for moving has a direction indicator.
                    View background = model.speed < 3 ? backgroundStationary : backgroundMoving;
                    Bitmap bm = Bitmap.createBitmap(sideLength, sideLength, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bm);

                    // Draw background.
                    background.layout(0, 0, sideLength, sideLength);
                    background.buildDrawingCache();
                    background.draw(canvas);
                    background.destroyDrawingCache();

                    // Rotate label so that it's orientated correctly.
                    canvas.save();
                    canvas.rotate((float) -model.bearing, halfLength, halfLength);

                    // Draw text on top.
                    journeyPatternRefLabel.setText(model.journeyPatternRef);
                    journeyPatternRefLabel.measure(widthSpec, heightSpec);
                    journeyPatternRefLabel.layout(0, 0, rect.width(), rect.height());
                    journeyPatternRefLabel.buildDrawingCache();
                    journeyPatternRefLabel.draw(canvas);
                    journeyPatternRefLabel.destroyDrawingCache();

                    canvas.restore();

                    model.icon = BitmapDescriptorFactory.fromBitmap(bm);
                }

                e.onComplete();
            }
        });
    }
}
