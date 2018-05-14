package com.kubiakpatryk.safely.data.prefs;

public interface PrefsHelper {

    boolean isFirstLaunch();

    boolean isShowingBytes();

    String getSavedPatternLock();

    String getSortOption();

    void setIsFirstLaunch(boolean value);

    void setIsShowingBytes(boolean value);

    void setPatternLock(String value);

    void setSortOption(String value);
}

