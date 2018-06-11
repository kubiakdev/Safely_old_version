package com.kubiakpatryk.safely.ui.tutorial;

import android.support.constraint.ConstraintLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

import java.util.List;

@PerActivity
public interface TutorialMvpPresenter<V extends TutorialMvpView> extends MvpPresenter<V> {

    void initializeCancelTextView(TextView tvCancel);

    void initializeRadioGroup(RadioGroup radioGroup);

    void onPatternLockClick();

    void onPinLockClick();

    void onPasswordLockClick();

    void onNoLockClick();

    void initializeConstraintLayouts(List<ConstraintLayout> layouts);
}
