package com.kubiakpatryk.safely.ui.tutorial;

import android.widget.RadioGroup;

import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.custom.CustomHorizontalScrollView;

public interface TutorialMvpView extends MvpView{

    CustomHorizontalScrollView getScrollView();

    RadioGroup getRadioGroup();

    int[] getRadioButtonsIdsArray();

    void openLoginActivity();
}
