package com.iblueroad.hyt.util;

import android.support.annotation.StringRes;
import android.widget.Toast;
import com.iblueroad.hyt.HytApp;

/**
 * Created by SkyXiao on 2017/9/7.
 */
public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void show(CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(HytApp.instance, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     */
    public static void show(@StringRes int textRes, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(HytApp.instance, textRes, duration);
        } else {
            mToast.setText(textRes);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void show(@StringRes int textRes) {
        show(textRes, Toast.LENGTH_SHORT);
    }
}