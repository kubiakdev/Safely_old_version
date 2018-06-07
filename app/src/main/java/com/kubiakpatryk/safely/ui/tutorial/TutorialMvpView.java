package com.kubiakpatryk.safely.ui.tutorial;

import android.widget.RadioGroup;

import com.kubiakpatryk.safely.ui.base.MvpView;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomHorizontalScrollView;

public interface TutorialMvpView extends MvpView{

    BaseActivity getBaseActivity();

    int[] getRadioButtonsIdsArray();

    CustomHorizontalScrollView getScrollView();

    RadioGroup getRadioGroup();

    String getString(int resId);

    void finish();

    void openLoginActivity(String result);

    void openSecureChooseActivity();

}
