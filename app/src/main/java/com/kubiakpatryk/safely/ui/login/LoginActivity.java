package com.kubiakpatryk.safely.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.login.password.LoginPasswordMvpPresenter;
import com.kubiakpatryk.safely.ui.login.pattern.LoginPatternMvpPresenter;
import com.kubiakpatryk.safely.ui.login.pin.LoginPinMvpPresenter;
import com.kubiakpatryk.safely.ui.main.MainActivity;
import com.kubiakpatryk.safely.ui.secure_choose.SecureChooseActivity;
import com.kubiakpatryk.safely.utils.AppConstants;

import javax.inject.Inject;

import butterknife.Unbinder;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginPasswordMvpPresenter<LoginMvpView> passwordPresenter;

    @Inject
    LoginPatternMvpPresenter<LoginMvpView> patternPresenter;

    @Inject
    LoginPinMvpPresenter<LoginMvpView> pinPresenter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void openSecureChooseActivity() {
        startActivity(SecureChooseActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void startVibration() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) vibrator.vibrate(300);
    }

    @Override
    public void restartActivity(String lockMethod) {
        Intent intent = getStartIntent(this);
        intent.putExtra(AppConstants.LOGIN_ACTIVITY_BUNDLE_NAME, lockMethod);
        startActivity(intent);
        finish();
    }

    @Override
    public void closeApp() {
        this.finishAffinity();
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void setUnBinder(Unbinder unBinder) {
        setUnbinder(unBinder);
    }

    @Override
    protected void onDestroy() {
        passwordPresenter.onDetach();
        patternPresenter.onDetach();
        pinPresenter.onDetach();
        super.onDestroy();
    }

    private void setContentView() {
        String result = "";
        if (getIntent().getExtras() != null)
            result = getIntent().getExtras().getString(AppConstants.LOGIN_ACTIVITY_BUNDLE_NAME);
        if (result != null) {
            switch (result) {
                case AppConstants.PATTERN_LOCK_METHOD:
                    initializePatternLock();
                    break;
                case AppConstants.PIN_LOCK_METHOD:
                    initializePinLock();
                    break;
                case AppConstants.PASSWORD_LOCK_METHOD:
                    initializePasswordLock();
                    break;
            }
        }
    }

    private void initializePasswordLock() {
        setContentView(R.layout.password_lock_layout);
        passwordPresenter.onAttach(this);
    }

    private void initializePatternLock() {
        setContentView(R.layout.pattern_lock_layout);
        patternPresenter.onAttach(this);
    }

    private void initializePinLock() {
        setContentView(R.layout.pin_lock_layout);
        pinPresenter.onAttach(this);
    }
}
