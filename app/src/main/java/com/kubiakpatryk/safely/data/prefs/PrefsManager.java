package com.kubiakpatryk.safely.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.kubiakpatryk.safely.di.annotations.ApplicationContext;
import com.kubiakpatryk.safely.utils.AppConstants;

import javax.inject.Inject;

public class PrefsManager implements PrefsHelper {

    private static final String PREFS_FIRST_LAUNCH_KEY = "PREFS_IS_FIRST_LAUNCH";
    private static final String PREFS_SHOW_BYTES_KEY = "PREFS_IS_SHOWING_BYTES";

    private final SharedPreferences preferences;

    @Inject
    PrefsManager(@ApplicationContext Context context){
        preferences = context.getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstLaunch(){
        return preferences.getBoolean(PREFS_FIRST_LAUNCH_KEY, true);
    }

    public void setIsFirstLaunch(boolean value){
        preferences.edit().putBoolean(PREFS_FIRST_LAUNCH_KEY, value).apply();
    }

    @Override
    public boolean isShowingBytes() {
        return preferences.getBoolean(PREFS_SHOW_BYTES_KEY, false);
    }

    @Override
    public void setIsShowingBytes(boolean value) {
        preferences.edit().putBoolean(PREFS_SHOW_BYTES_KEY, value).apply();
    }
}
