package com.kubiakpatryk.safely.ui.login.pin;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.login.LoginMvpView;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class LoginPinPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginPinMvpPresenter<V> {

    @Inject
    LoginPinPresenter(DataManager dataManager,
                      SchedulerProviderHelper schedulerProviderHelper,
                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    private String firstTryValue = "";
    private int lives = 3;

    @BindView(R.id.pin_lock_layout_pin_lock)
    PinLockView pinLockView;

    @BindView(R.id.pin_lock_layout_indicatorDots_main)
    IndicatorDots indicatorDots;

    @BindView(R.id.pin_lock_layout_text_view_title)
    TextView title;

    @BindView(R.id.pin_lock_layout_text_view_pin)
    TextView pinTextView;

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        Unbinder unbinder = ButterKnife.bind(this, getMvpView().getActivity());
        getMvpView().setUnBinder(unbinder);
        initializeLock();
    }

    private void initializeLock() {
        if (getDataManager().getLockMethod().equals(AppConstants.PIN_LOCK_METHOD)
                && !getDataManager().getLock().equals(""))
            title.setText(R.string.pinLock_selectYourLock);
        else title.setText(R.string.pinLock_chooseYourLock);

        pinLockView.attachIndicatorDots(indicatorDots);
        pinLockView.setPinLockListener(new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                if (getDataManager().getLock().equals("")
                        && getDataManager().getLockMethod().equals("")) {
                    if (firstTryValue.equals("")) onFirstTryAddLock(pin);
                    else onSecondTryAddLock(pin);
                } else {
                    if (getDataManager().getLock().equals(pin)) onGoodPattern();
                    else onWrongPattern();
                }
            }

            @Override
            public void onEmpty() {
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
            }
        });
    }

    private void onFirstTryAddLock(String pin) {
        firstTryValue = pin;
        title.setText(R.string.pinLock_chooseYourLockAgain);
        pinLockView.resetPinLockView();
    }

    private void onSecondTryAddLock(String pin) {
        if (firstTryValue.equals(pin)) onLocksAreSame();
        else onLocksAreDifferent();
    }

    private void onLocksAreSame() {
        Single.fromCallable(() -> {
            indicatorDots.setVisibility(View.GONE);
            pinTextView.setText(firstTryValue);
            pinLockView.setTextColor(getMvpView().getResources().getColor(R.color.lockCorrect));
            title.setText(R.string.loginActivity_rememberThat);
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_LONG_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    getDataManager().setLock(firstTryValue);
                    getDataManager().setLockMethod(AppConstants.PIN_LOCK_METHOD);
                    getDataManager().setIsFirstLaunch(false);
                    getMvpView().openSecureChooseActivity();
                    getMvpView().finish();
                });
    }

    private void onLocksAreDifferent() {
        Single.fromCallable(() -> {
            pinLockView.setTextColor(getMvpView().getResources().getColor(R.color.lockWrong));
            title.setText(R.string.pinLock_locksMustBeTheSame);
            getMvpView().startVibration();
            shakeLockView();
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_LONG_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> getMvpView().restartActivity(AppConstants.PIN_LOCK_METHOD));

    }

    private void onGoodPattern() {
        pinLockView.setTextColor(getMvpView().getResources().getColor(R.color.lockCorrect));
        getMvpView().openMainActivity();
        getMvpView().finish();
    }

    private void onWrongPattern() {
        Single.fromCallable(() -> {
            pinLockView.setTextColor(getMvpView().getResources().getColor(R.color.lockWrong));
            getMvpView().startVibration();
            shakeLockView();
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_SHORT_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    pinLockView.resetPinLockView();
                    pinLockView.setTextColor(getMvpView().getResources()
                            .getColor(R.color.lockNormal));
                    lives--;
                    if (lives == 2) title.setText(R.string.loginActivity_tryAgain);
                    else if (lives == 1) title.setText(R.string.loginActivity_yourLastChance);
                    else if (lives == 0) getMvpView().closeApp();
                });
    }

    private void shakeLockView() {
        pinLockView.startAnimation(AnimationUtils.loadAnimation(
                pinLockView.getContext(),
                R.anim.login_activity_shaking_pattern_view));
        indicatorDots.startAnimation(AnimationUtils.loadAnimation(
                indicatorDots.getContext(),
                R.anim.login_activity_shaking_pattern_view));
    }
}
