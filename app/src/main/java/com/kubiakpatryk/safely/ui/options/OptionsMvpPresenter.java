package com.kubiakpatryk.safely.ui.options;

import android.widget.Switch;
import android.widget.TextView;

import com.kubiakpatryk.safely.ui.base.MvpPresenter;
import com.kubiakpatryk.safely.ui.base.MvpView;

public interface OptionsMvpPresenter<V extends MvpView> extends MvpPresenter<V>{

    void initializeChangeFontSizeTextView(TextView textView);

    void initializeChangeRecyclerColorSample();

    void initializeShowBytesSwitch(Switch showBytesSwitch);

    void onChangeRecyclerColor(String[] colors);
}
