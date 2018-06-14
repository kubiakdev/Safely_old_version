package com.kubiakpatryk.safely.ui.login.password;

import android.graphics.PorterDuff;
import android.support.annotation.ColorRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.login.LoginMvpView;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class LoginPasswordPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginPasswordMvpPresenter<V> {

    private String firstTryValue = "";
    private int lives = 3;

    @BindView(R.id.password_lock_layout_edit_text)
    AppCompatEditText editText;

    @BindView(R.id.password_lock_layout_button_save)
    Button button;

    @BindView(R.id.password_lock_layout_constraint_layout)
    ConstraintLayout layout;

    @BindView(R.id.password_lock_layout_text_view_title)
    TextView title;

    @Inject
    LoginPasswordPresenter(DataManager dataManager,
                           SchedulerProviderHelper schedulerProviderHelper,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        Unbinder unbinder = ButterKnife.bind(this, getMvpView().getActivity());
        getMvpView().setUnBinder(unbinder);
        initializeLock();
        CommonUtils.showSoftKeyboard(getMvpView().getWindow());
        layout.setOnClickListener(v -> CommonUtils.hideSoftKeyboard(getMvpView().getActivity()));
    }

    private void initializeLock() {
        if (getDataManager().getLockMethod().equals(AppConstants.PASSWORD_LOCK_METHOD)
                && !getDataManager().getLock().equals("")
                && !AppStatics.IS_IN_CHANGE_LOCK_METHOD_MODE)
            title.setText(R.string.passwordLock_selectYourLock);
        else {
            title.setText(R.string.passwordLock_chooseYourLock);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(v -> {
                String password = editText.getText().toString();
                if (password.length() != 0) onFirstTryAddLock(password);
                else showToast();
            });
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = getDataManager().getLock();
                if (!password.equals("") && !AppStatics.IS_IN_CHANGE_LOCK_METHOD_MODE) {
                    if (password.length() == charSequence.toString().length()) {
                        if (password.equals(charSequence.toString())) onGoodPassword();
                        else onWrongPassword();
                    }
                } else if (firstTryValue.length() == (charSequence.toString().length())
                        && firstTryValue.length() > 0)
                    onSecondTryAddLock(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void onFirstTryAddLock(String password) {
        firstTryValue = password;
        title.setText(R.string.passwordLock_chooseYourLockAgain);
        editText.setText("");
    }

    private void onSecondTryAddLock(String password) {
        if (firstTryValue.equals(password)) onLocksAreSame();
        else onLocksAreDifferent();
    }

    private void onLocksAreSame() {
        Single.fromCallable(() -> {
            getMvpView().disableBackButton();
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            button.setTextColor(getMvpView().getResources().getColor(R.color.lockCorrect));
            editText.setTextColor(getMvpView().getResources().getColor(R.color.lockCorrect));
            setEditTextTintColor(R.color.lockCorrect);
            title.setText(R.string.loginActivity_rememberThat);
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_LONG_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    getDataManager().setLock(firstTryValue);
                    getDataManager().setLockMethod(AppConstants.PASSWORD_LOCK_METHOD);
                    getDataManager().setIsFirstLaunch(false);
                    getMvpView().openSecureChooseActivity();
                    getMvpView().finish();
                    setEditTextTintColor(R.color.lockNormal);
                });
    }

    private void onLocksAreDifferent() {
        Single.fromCallable(() -> {
            button.setTextColor(getMvpView().getResources().getColor(R.color.lockWrong));
            editText.setTextColor(getMvpView().getResources().getColor(R.color.lockWrong));
            setEditTextTintColor(R.color.lockWrong);
            title.setText(R.string.passwordLock_locksMustBeTheSame);
            getMvpView().startVibration();
            shakeLockView();
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_LONG_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    getMvpView().restartActivity(AppConstants.PASSWORD_LOCK_METHOD);
                    setEditTextTintColor(R.color.lockNormal);
                });

    }

    private void onGoodPassword() {
        editText.setTextColor(getMvpView().getResources().getColor(R.color.lockCorrect));
        editText.getBackground().mutate().setColorFilter(getMvpView().getResources()
                .getColor(R.color.lockCorrect), PorterDuff.Mode.SRC_ATOP);
        if (AppStatics.IS_IN_RE_ENTERING_LOCK_METHOD_MODE){
            AppStatics.IS_IN_RE_ENTERING_LOCK_METHOD_MODE = false;
            AppStatics.IS_IN_CHANGE_LOCK_METHOD_MODE = true;
            getMvpView().openTutorialActivity();
        }
        else getMvpView().openMainActivity();
        getMvpView().finish();
    }

    private void onWrongPassword() {
        Single.fromCallable(() -> {
            editText.setTextColor(getMvpView().getResources().getColor(R.color.lockWrong));
            editText.getBackground().mutate().setColorFilter(getMvpView().getResources()
                    .getColor(R.color.lockWrong), PorterDuff.Mode.SRC_ATOP);
            getMvpView().startVibration();
            shakeLockView();
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_SHORT_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    editText.setText("");
                    lives--;
                    if (lives == 2) title.setText(R.string.loginActivity_tryAgain);
                    else if (lives == 1) title.setText(R.string.loginActivity_yourLastChance);
                    else if (lives == 0) getMvpView().closeApp();
                    setEditTextTintColor(R.color.lockNormal);
                });
    }

    private void shakeLockView() {
        editText.startAnimation(AnimationUtils.loadAnimation(
                editText.getContext(),
                R.anim.login_activity_shaking_pattern_view));
        button.startAnimation(AnimationUtils.loadAnimation(
                button.getContext(),
                R.anim.login_activity_shaking_pattern_view));
    }

    private void setEditTextTintColor(@ColorRes int id) {
        editText.getBackground().mutate().setColorFilter(getMvpView().getResources()
                .getColor(id), PorterDuff.Mode.SRC_ATOP);
    }

    private void showToast() {
        Toast toast = Toast.makeText(button.getContext(),
                R.string.passwordLock_minimalPasswordLength, Toast.LENGTH_SHORT);
        TextView view = toast.getView().findViewById(android.R.id.message);
        if (view != null) view.setGravity(Gravity.CENTER);
        toast.show();
    }
}
