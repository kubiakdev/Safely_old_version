package com.kubiakpatryk.safely.ui.options;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

    @BindView(R.id.optionsActivity_cardView_changeRecyclerColor_sample)
    CardView changeRecyclerColorSample;

//    @BindArray(R.array.mainActivity_customRecyclerColorsArray)
//    int[] recyclerColorsArray;

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
        initializeChangeRecyclerColorSample();
    }

    @Override
    public void onBackPressed() {
        onReloadAdapterListCallback.reloadAdapter();
        super.onBackPressed();
    }

    @Override
    public CardView getChangeRecyclerColorSample() {
        return changeRecyclerColorSample;
    }

    @Override
    public String[] getRecyclerColorsArray() {
        return this.getResources().getStringArray(R.array.mainActivity_customRecyclerColorsArray);
    }

    private void initializeChangeRecyclerColorSample(){
        presenter.initializeChangeRecyclerColorSample();
    }

    private void initializeShowBytesSwitch(){
        presenter.initializeShowBytesSwitch(showBytesSwitch);
    }

    @OnClick(R.id.optionsActivity_leftArrowButton)
    public void onLeftArrowButtonClick(){
        onReloadAdapterListCallback.reloadAdapter();
        finish();
    }

    @OnClick(R.id.optionsActivity_cardView_changeRecyclerColor)
    public void onChangeRecyclerColor(){
        presenter.onChangeRecyclerColor(getRecyclerColorsArray());
    }

    @OnClick(R.id.optionsActivity_cardView_changeSecureMethod)
    public void onChangeSecureMethodClick(){
        Toast.makeText(this, R.string.general_notAvailableYet, Toast.LENGTH_SHORT).show();
    }

    public interface OnReloadAdapterListCallback {
        void reloadAdapter();
    }
}
