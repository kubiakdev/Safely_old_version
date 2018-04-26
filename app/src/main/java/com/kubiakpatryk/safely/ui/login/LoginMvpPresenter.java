package com.kubiakpatryk.safely.ui.login;

import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void lookAfterPatternLock(PatternLockView patternLockView, TextView textView);
}
