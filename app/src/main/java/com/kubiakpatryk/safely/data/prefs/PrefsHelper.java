package com.kubiakpatryk.safely.data.prefs;

public interface PrefsHelper {

    boolean isFirstLaunch();

    void setIsFirstLaunch(boolean value);

    boolean isShowingBytes();

    void setIsShowingBytes(boolean value);

    String getSavedPatternLock();

    void setPatternLock(String value);
}

