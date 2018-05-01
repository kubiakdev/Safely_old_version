package com.kubiakpatryk.safely.ui.main.mvp.cipher;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

@PerActivity
public interface MainCipherMvpPresenter<V extends MainCipherMvpView> extends MvpPresenter<V>{

    String encrypt(final String source);

    String decrypt(final String source);
}
