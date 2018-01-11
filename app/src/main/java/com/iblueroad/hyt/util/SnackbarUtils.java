package com.iblueroad.hyt.util;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.iblueroad.hyt.R;
import java.lang.ref.WeakReference;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * Created by SkyXiao on 2017/9/15.
 */
public final class SnackbarUtils {
    private static WeakReference<Snackbar> snackbarWeakReference;

    private SnackbarUtils() {
        throw new AssertionError();
    }

    public static Snackbar show(@NonNull final View target, @NonNull final String text, int bkg, int front) {
        if (TextUtils.isEmpty(text)) return null;
        //        final Snackbar snackbar = Snackbar.make(target, text, Snackbar.LENGTH_SHORT);

        snackbarWeakReference = new WeakReference<>(Snackbar.make(target, text, Snackbar.LENGTH_SHORT));
        Snackbar snackbar = snackbarWeakReference.get();

        View view = snackbar.getView();
        view.setBackgroundColor(bkg);

        //                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        //                params.gravity = Gravity.TOP;
        //                view.setLayoutParams(params);

        TextView tv = view.findViewById(R.id.snackbar_text);
        tv.setTextColor(front);
        tv.setGravity(Gravity.CENTER);
        if (SDK_INT >= KITKAT) {
            tv.setHeight(-1);
//            view.setPadding(0, DisplayInfo.getStatusBarHeight(), 0, 0);
            view.setPadding(0, 22, 0, 0);
        }

        try {
            snackbar.show();
            return snackbar;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}