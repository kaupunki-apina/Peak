package fi.salminen.tomy.peak.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


public class Ui {

    public static boolean isTranslucentStatusBar(AppCompatActivity activity) {
        Window w = activity.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        int flags = lp.flags;
        return (flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    }

    public static int getStatusBarHeight(Context context) {
        // status bar height
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }
}
