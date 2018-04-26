package com.kubiakpatryk.safely.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

        lookAfterPatternLock();
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

    private void lookAfterPatternLock(){
        presenter.lookAfterPatternLock(patternLockView, textView);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
