package com.kubiakpatryk.safely.ui.login;

import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.kubiakpatryk.safely.ui.base.MvpView;

public interface LoginMvpView extends MvpView{

    void openMainActivity();

    void openSecureChooseActivity();

    void startVibration();

    void restartActivity();

    void closeApp();

    PatternLockView getPatternLockView();

    TextView getTextView();
}
