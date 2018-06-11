package com.kubiakpatryk.safely.ui.options;

import android.widget.Switch;
import android.widget.TextView;

import com.kubiakpatryk.safely.ui.base.MvpPresenter;
import com.kubiakpatryk.safely.ui.base.MvpView;

public interface OptionsMvpPresenter<V extends MvpView> extends MvpPresenter<V>{

    String getLockMethod();

    void initializeChangeFontSizeTextView(TextView textView);

    void initializeChangeLanguageTextView(TextView textView);

    void initializeChangeRecyclerColorSample();

    void onChangeSecureMethod();

    void initializeShowBytesSwitch(Switch showBytesSwitch);

    void onChangeRecyclerColor(String[] colors);
}
