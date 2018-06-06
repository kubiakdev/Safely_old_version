package com.kubiakpatryk.safely.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.login.LoginActivity;
import com.kubiakpatryk.safely.ui.tutorial.TutorialActivity;
import com.kubiakpatryk.safely.utils.AppConstants;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashMvpPresenter<SplashMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        presenter.setLanguage();
        presenter.selectSuitableActivity();
    }

    @Override
    public void openTutorialActivity() {
        startActivity(TutorialActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(this);
        String lockMethod = presenter.getLockMethod();
        intent.putExtra(AppConstants.LOGIN_ACTIVITY_BUNDLE_NAME, lockMethod);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
