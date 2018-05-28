package com.kubiakpatryk.safely.ui.options;

import android.widget.Switch;

import com.kubiakpatryk.safely.ui.base.MvpPresenter;
import com.kubiakpatryk.safely.ui.base.MvpView;

public interface OptionsMvpPresenter<V extends MvpView> extends MvpPresenter<V>{

    void initializeShowBytesSwitch(Switch showBytesSwitch);
}
