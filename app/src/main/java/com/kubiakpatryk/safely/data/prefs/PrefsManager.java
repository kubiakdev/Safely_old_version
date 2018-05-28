package com.kubiakpatryk.safely.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.kubiakpatryk.safely.di.annotations.ApplicationContext;
import com.kubiakpatryk.safely.utils.AppConstants;

import javax.inject.Inject;

public class PrefsManager implements PrefsHelper {

    private final String PREFS_FIRST_LAUNCH_KEY = "PREFS_IS_FIRST_LAUNCH";
    private final String PREFS_LAST_NOTE_ID_KEY = "PREFS_LAST_NOTE_ID";
    private final String PREFS_SAVED_PATTERN_LOCK_KEY = "PREFS_SAVED_PATTERN_LOCK";
    private final String PREFS_SORT_OPTION_KEY = "PREFS_SORT_OPTION";

    private final SharedPreferences preferences;

    @Inject
    PrefsManager(@ApplicationContext Context context) {
        preferences = context.getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstLaunch() {
        return preferences.getBoolean(PREFS_FIRST_LAUNCH_KEY, true);
    }

    public void setIsFirstLaunch(boolean value) {
        preferences.edit().putBoolean(PREFS_FIRST_LAUNCH_KEY, value).apply();
    }

    @Override
    public Long getLastNoteId() {
        return preferences.getLong(PREFS_LAST_NOTE_ID_KEY, -1L);
    }

    @Override
    public void setLastNoteId(long id) {
        preferences.edit().putLong(PREFS_LAST_NOTE_ID_KEY, id).apply();
    }

    @Override
    public String getSavedPatternLock() {
        return preferences.getString(PREFS_SAVED_PATTERN_LOCK_KEY, "");
    }

    @Override
    public void setPatternLock(String value) {
        preferences.edit().putString(PREFS_SAVED_PATTERN_LOCK_KEY, value).apply();
    }

    @Override
    public String getSortOption() {
        return preferences.getString(PREFS_SORT_OPTION_KEY, "To latest");
    }

    @Override
    public void setSortOption(String value) {
        preferences.edit().putString(PREFS_SORT_OPTION_KEY, value).apply();
    }
}
