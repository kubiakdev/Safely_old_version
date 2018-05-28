package com.kubiakpatryk.safely.ui.options;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.main.mvp.MainPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OptionsActivity extends BaseActivity implements OptionsMvpView {

    public static MainPresenter.OnReloadAdapterListCallback onReloadAdapterListCallback;

    @Inject
    OptionsMvpPresenter<OptionsMvpView> presenter;

    @BindView(R.id.optionsActivity_switch_showBytes)
    Switch showBytesSwitch;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);

        initializeShowBytesSwitch();
    }

    @Override
    public void onBackPressed() {
        onReloadAdapterListCallback.reloadAdapter();
        super.onBackPressed();
    }

    private void initializeShowBytesSwitch(){
        presenter.initializeShowBytesSwitch(showBytesSwitch);
    }

    @OnClick(R.id.optionsActivity_leftArrowButton)
    public void onLeftArrowButtonClick(){
        onReloadAdapterListCallback.reloadAdapter();
        finish();
    }

    @OnClick(R.id.optionsActivity_cardView_changeSecureMethod)
    public void onChangeSecureMethodClick(){
        Toast.makeText(this, "Not available yet", Toast.LENGTH_SHORT).show();
    }

    public interface OnReloadAdapterListCallback {
        void reloadAdapter();
    }
}