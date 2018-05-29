package com.kubiakpatryk.safely.data.prefs;

public interface PrefsHelper {

    boolean isFirstLaunch();

    Long getLastNoteId();

    String getRecyclerColor();

    String getPatternLock();

    String getSortOption();

    void setIsFirstLaunch(boolean value);

    void setLastNoteId(long id);

    void setPatternLock(String value);

    void setRecyclerColor(String color);

    void setSortOption(String value);
}

