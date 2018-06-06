package com.kubiakpatryk.safely.ui.login;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import com.kubiakpatryk.safely.ui.base.MvpView;

import butterknife.Unbinder;

public interface LoginMvpView extends MvpView{

    AppCompatActivity getActivity();

    Resources getResources();

    void openMainActivity();

    void openSecureChooseActivity();

    void startVibration();

    void restartActivity(String lockMethod);

    void closeApp();

    void setUnBinder(Unbinder unBinder);
}
