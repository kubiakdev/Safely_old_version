package com.kubiakpatryk.safely.ui.tutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.ui.base.activity.BaseActivity;
import com.kubiakpatryk.safely.ui.custom.CustomHorizontalScrollView;
import com.kubiakpatryk.safely.ui.login.LoginActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialActivity extends BaseActivity implements TutorialMvpView {

    private int[] radioButtonsIdsArray = {R.id.tutorialActivity_radioButton_welcome,
            R.id.tutorialActivity_radioButton_byteSafety,
            R.id.tutorialActivity_radioButton_internetSafety,
            R.id.tutorialActivity_radioButton_secureMethods};

    @Inject
    TutorialMvpPresenter<TutorialMvpView> presenter;

    @Inject
    @Named("ScrollView")
    CustomHorizontalScrollView scrollView;

    @BindViews({R.id.tutorialActivity_constraintLayout_welcome,
            R.id.tutorialActivity_constraintLayout_safety,
            R.id.tutorialActivity_constraintLayout_backup,
            R.id.tutorialActivity_constraintLayout_internet_safety,
            R.id.tutorialActivity_constraintLayout_secure_methods})
    List<ConstraintLayout> constraintLayoutList;

    @BindView(R.id.tutorialActivity_radioGroup)
    RadioGroup radioGroup;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, TutorialActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        presenter.onAttach(this);
        presenter.setLayoutParameters(constraintLayoutList);
        initializeRadioGroup();
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @OnClick(R.id.tutorialActivity_constraintLayout_patternLock)
    public void onPatternLockClick() {
        presenter.onPatternLockClick();
    }


    @OnClick(R.id.tutorialActivity_constraintLayout_pinLock)
    public void onPinLockClick() {
        Toast.makeText(this, R.string.general_notAvailableYet, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tutorialActivity_constraintLayout_passwordLock)
    public void onPasswordLockClick() {
        Toast.makeText(this, R.string.general_notAvailableYet, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tutorialActivity_constraintLayout_noLock)
    public void onLackLockClick() {
        Toast.makeText(this, R.string.general_notAvailableYet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public CustomHorizontalScrollView getScrollView() {
        return scrollView;
    }

    @Override
    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    @Override
    public int[] getRadioButtonsIdsArray() {
        return radioButtonsIdsArray;
    }

    private void initializeRadioGroup() {
        presenter.initializeRadioGroup(radioGroup);
    }
}

