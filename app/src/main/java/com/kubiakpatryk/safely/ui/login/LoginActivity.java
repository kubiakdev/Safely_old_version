package com.kubiakpatryk.safely.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.main.MainActivity;
import com.kubiakpatryk.safely.ui.secure_choose.SecureChooseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> presenter;

    @BindView(R.id.login_activity_pattern_lock_view)
    PatternLockView patternLockView;

    @BindView(R.id.login_activity_text_view)
    TextView textView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);

        initializePatternLock();
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
    public void restartActivity() {
        startActivity(getStartIntent(this));
        finish();
    }

    @Override
    public void closeApp() {
        this.finishAffinity();
    }

    @Override
    public PatternLockView getPatternLockView() {
        return patternLockView;
    }

    @Override
    public TextView getTextView() {
        return textView;
    }

    private void initializePatternLock() {
        presenter.initializePatternLock();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
