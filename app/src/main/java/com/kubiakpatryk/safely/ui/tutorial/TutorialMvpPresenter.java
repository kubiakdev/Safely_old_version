package com.kubiakpatryk.safely.ui.tutorial;

import android.support.constraint.ConstraintLayout;
import android.widget.RadioGroup;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

import java.util.List;

@PerActivity
public interface TutorialMvpPresenter<V extends TutorialMvpView> extends MvpPresenter<V> {

    void initializeRadioGroup(RadioGroup radioGroup);

    void onPatternLockClick();

    void onPinLockClick();

    void onPasswordLockClick();

    void onLackLockClick();

    void setLayoutParameters(List<ConstraintLayout> layouts);
}
