package com.kubiakpatryk.safely.ui.secure_choose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.main.MainActivity;
import com.kubiakpatryk.safely.utils.AppStatics;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SecureChooseActivity extends BaseActivity implements SecureChooseMvpView {

    @Inject
    SecureChooseMvpPresenter<SecureChooseMvpView> presenter;

    public static Intent getStartIntent(Context context){
        return new Intent(context, SecureChooseActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_choose);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        if (AppStatics.IS_IN_CHANGE_LOCK_METHOD_MODE){
            openMainActivity();
            finish();
        }
        else presenter.onGenerateCipherButtonClick();
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getStartIntent(this));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
