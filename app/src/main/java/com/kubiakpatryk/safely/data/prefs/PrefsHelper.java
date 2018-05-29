package com.kubiakpatryk.safely.data.prefs;

public interface PrefsHelper {

    boolean isFirstLaunch();

    Long getLastNoteId();

    Long getFontSize();

    String getLanguage();

    String getPatternLock();

    String getRecyclerColor();

    String getSortOption();

    void setIsFirstLaunch(boolean value);

    void setFontSize(Long value);

    void setLanguage(String language);

    void setLastNoteId(long id);

    void setPatternLock(String value);

    void setRecyclerColor(String color);

    void setSortOption(String value);
}

