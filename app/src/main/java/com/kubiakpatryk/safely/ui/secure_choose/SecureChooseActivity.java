package com.kubiakpatryk.safely.ui.secure_choose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

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
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getStartIntent(this));
        finish();
    }

    @OnClick(R.id.secureChooseActivity_button_generateCipher)
    public void onGenerateCipherButtonClick(Button button){
        button.setClickable(false);
        presenter.onGenerateCipherButtonClick();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
