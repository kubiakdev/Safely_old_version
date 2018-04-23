package com.kubiakpatryk.safely.utils;

import android.content.res.Resources;

public final class ScreenUtils {

    private ScreenUtils() {}

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
