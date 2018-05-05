package com.kubiakpatryk.safely.ui.login;

import android.view.animation.AnimationUtils;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    private int patternTryCount = 0;
    private String patternFirstTry;
    private int lives = 3;

    @Inject
    LoginPresenter(DataManager dataManager,
                   SchedulerProviderHelper schedulerProviderHelper,
                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void initializePatternLock() {
        if (!getDataManager().getSavedPatternLock().equals(""))
            getMvpView().getTextView().setText(R.string.patternLock_selectYourPattern);
        else getMvpView().getTextView().setText(R.string.patternLock_chooseYourPattern);

        RxPatternLockView.patternChanges(getMvpView().getPatternLockView())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribeOn(getSchedulerProviderHelper().io())
                .subscribe(event -> {
                    if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                        if (getDataManager().getSavedPatternLock().equals("")) {
                            if (patternTryCount == 0) onFirstTryAddPatternLock(event);
                            else if (patternTryCount == 1) onSecondTryAddPatternLock(event);
                        } else {
                            if (getDataManager().getSavedPatternLock().equals(PatternLockUtils
                                    .patternToString(getMvpView().getPatternLockView(),
                                            event.getPattern()))) onGoodPattern();
                            else onWrongPattern();
                        }
                    }
                });

    }

    private void onFirstTryAddPatternLock(PatternLockCompoundEvent event) {
        patternFirstTry = PatternLockUtils.patternToString(getMvpView().getPatternLockView(),
                event.getPattern());
        patternTryCount++;
        getMvpView().getTextView().setText(R.string.patternLock_selectYourPatternAgain);
        getMvpView().getPatternLockView().clearPattern();
    }

    private void onSecondTryAddPatternLock(PatternLockCompoundEvent event) {
        String patternSecondTry = PatternLockUtils.patternToString(getMvpView().getPatternLockView(),
                event.getPattern());
        if (patternFirstTry.equals(patternSecondTry)) onPatternLocksAreSame(patternSecondTry);
        else onPatternLocksAreDifferent();
    }

    private void onPatternLocksAreSame(final String patternSecondTry) {
        Single.fromCallable(() -> {
            getMvpView().getPatternLockView().setViewMode(PatternLockView.PatternViewMode.CORRECT);
            getMvpView().getTextView().setText(R.string.patternLock_rememberThat);
            return getMvpView().getPatternLockView().getPatternViewMode();
        })
                .delay(1, TimeUnit.SECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    getDataManager().setPatternLock(patternSecondTry);
                    patternTryCount++;
                    getMvpView().openSecureChooseActivity();
                });
    }

    private void onPatternLocksAreDifferent() {
        Single.fromCallable(() -> {
            getMvpView().getPatternLockView().setViewMode(PatternLockView.PatternViewMode.WRONG);
            getMvpView().getTextView().setText(R.string.patternLock_patternsMustBeTheSame);
            getMvpView().startVibration();
            shakePatternLockView();
            return getMvpView().getPatternLockView().getPatternViewMode();
        })
                .delay(1, TimeUnit.SECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> getMvpView().restartActivity());

    }

    private void onGoodPattern() {
        getMvpView().getPatternLockView().setViewMode(
                PatternLockView.PatternViewMode.CORRECT);
        getMvpView().openMainActivity();
    }

    private void onWrongPattern() {
        Single.fromCallable(() -> {
            getMvpView().getPatternLockView().setViewMode(PatternLockView.PatternViewMode.WRONG);
            getMvpView().startVibration();
            shakePatternLockView();
            return getMvpView().getPatternLockView().getPatternViewMode();
        })
                .delay(300, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    getMvpView().getPatternLockView().clearPattern();
                    lives--;
                    if (lives == 2) getMvpView().getTextView()
                            .setText(R.string.patternLock_tryAgain);
                    else if (lives == 1) getMvpView().getTextView()
                            .setText(R.string.patternLock_yourLastChance);
                    else if (lives == 0) getMvpView().closeApp();
                });
    }

    private void shakePatternLockView() {
        getMvpView().getPatternLockView().startAnimation(AnimationUtils.loadAnimation(
                getMvpView().getPatternLockView().getContext(),
                R.anim.login_activity_shaking_pattern_view));
    }
}
