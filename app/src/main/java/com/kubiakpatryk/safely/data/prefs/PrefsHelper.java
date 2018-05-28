package com.kubiakpatryk.safely.data.prefs;

public interface PrefsHelper {

    boolean isFirstLaunch();

    Long getLastNoteId();

    String getSavedPatternLock();

    String getSortOption();

    void setIsFirstLaunch(boolean value);

    void setLastNoteId(long id);

    void setPatternLock(String value);

    void setSortOption(String value);
}

