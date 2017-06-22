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
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel;

import java.util.LinkedList;
import java.util.List;

import fi.salminen.tomy.peak.R;
import fi.salminen.tomy.peak.persistence.models.BusModel;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class IconFactory {

    private Context context;
    private TextView journeyPatternRefLabel;
    private View backgroundStationary;
    private View backgroundMoving;
    private int sideLength;
    private int halfLength;

    IconFactory(Context context, int resIdLabel, int resIdStationary, int resIdMoving) {
        this.context = context;
        this.journeyPatternRefLabel = (TextView) inflate(resIdLabel);
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

    public Observable<Void> getBusIcon(List<BusModel> models) {
        LinkedList<Result<BusModel>> results = new LinkedList<>();

        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                for (BusModel model : models) {
                    // Icon for moving has a direction indicator.
                    View background = model.speed == 0 ? backgroundStationary : backgroundMoving;
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
                    model.icon = BitmapDescriptorFactory.fromBitmap(bm);
                }

                e.onComplete();
            }
        });
    }

    public class Result<T extends BaseRXModel> {
        public T model;
        public BitmapDescriptor icon;

        Result(T model, BitmapDescriptor icon) {
            this.model = model;
            this.icon = icon;
        }
    }
}
