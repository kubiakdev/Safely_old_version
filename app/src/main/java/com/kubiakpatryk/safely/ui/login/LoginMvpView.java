package com.kubiakpatryk.safely.ui.login;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.kubiakpatryk.safely.ui.base.MvpView;

import butterknife.Unbinder;

public interface LoginMvpView extends MvpView{

    AppCompatActivity getActivity();

    Resources getResources();

    Window getWindow();

    void finish();

    void openMainActivity();

    void openSecureChooseActivity();

    void openTutorialActivity();

    void startVibration();

    void restartActivity(String lockMethod);

    void closeApp();

    void setUnBinder(Unbinder unBinder);

    void disableBackButton();
}
