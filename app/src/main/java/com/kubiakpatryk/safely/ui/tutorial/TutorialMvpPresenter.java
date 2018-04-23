package com.kubiakpatryk.safely.ui.tutorial;

import android.support.constraint.ConstraintLayout;

import com.kubiakpatryk.safely.di.annotations.PerActivity;
import com.kubiakpatryk.safely.ui.base.MvpPresenter;

import java.util.List;

@PerActivity
public interface TutorialMvpPresenter<V extends TutorialMvpView> extends MvpPresenter<V> {

    void onAgreeButtonClick();

    void setLayoutParameters(List<ConstraintLayout> layouts);
}
