package com.kubiakpatryk.safely.ui.secure_choose;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

@PerActivity
public interface SecureChooseMvpPresenter<V extends SecureChooseMvpView> extends MvpPresenter<V> {

    void onGenerateCipherButtonClick();
}
