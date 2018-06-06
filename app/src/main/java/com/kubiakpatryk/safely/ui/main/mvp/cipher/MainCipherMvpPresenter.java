package com.kubiakpatryk.safely.ui.main.mvp.cipher;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;
import com.kubiakpatryk.safely.ui.base.MvpView;

@PerActivity
public interface MainCipherMvpPresenter<V extends MvpView> extends MvpPresenter<V>{

    String encrypt(final String source);

    String decrypt(final String source);
}
