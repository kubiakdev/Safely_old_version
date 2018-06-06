package com.kubiakpatryk.safely.ui.login.pattern;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;
import com.kubiakpatryk.safely.ui.login.LoginMvpView;

@PerActivity
public interface LoginPatternMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {
}
