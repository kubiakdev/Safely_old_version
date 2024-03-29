package com.kubiakpatryk.safely.ui.login.pattern;

import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;
import com.kubiakpatryk.safely.R;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.login.LoginMvpView;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.AppStatics;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class LoginPatternPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginPatternMvpPresenter<V> {

    private String firstTryValue = "";
    private int lives = 3;

    @BindView(R.id.pattern_lock_layout_pattern_lock)
    PatternLockView patternLockView;

    @BindView(R.id.pattern_lock_layout_text_view_title)
    TextView title;

    @Inject
    LoginPatternPresenter(DataManager dataManager,
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
    }

    private void initializeLock() {
        if (getDataManager().getLockMethod().equals(AppConstants.PATTERN_LOCK_METHOD)
                && !getDataManager().getLock().equals(""))
            title.setText(R.string.patternLock_selectYourLock);
        else title.setText(R.string.patternLock_chooseYourLock);

        RxPatternLockView.patternChanges(patternLockView)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribeOn(getSchedulerProviderHelper().io())
                .subscribe(event -> {
                    if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                        if ((getDataManager().getLock().equals("")
                                && getDataManager().getLockMethod().equals(""))
                                || AppStatics.IS_IN_CHANGE_LOCK_METHOD_MODE) {
                            if (firstTryValue.equals("")) onFirstTryAddLock(event);
                            else onSecondTryAddLock(event);
                        } else {
                            if (getDataManager().getLock().equals(PatternLockUtils
                                    .patternToString(patternLockView,
                                            event.getPattern()))) onGoodPattern();
                            else onWrongPattern();
                        }
                    }
                });

    }

    private void onFirstTryAddLock(PatternLockCompoundEvent event) {
        firstTryValue = PatternLockUtils.patternToString(patternLockView,
                event.getPattern());
        title.setText(R.string.patternLock_chooseYourLockAgain);
        patternLockView.clearPattern();
    }

    private void onSecondTryAddLock(PatternLockCompoundEvent event) {
        String patternSecondTry = PatternLockUtils.patternToString(patternLockView,
                event.getPattern());
        if (firstTryValue.equals(patternSecondTry)) onLocksAreSame();
        else onLocksAreDifferent();
    }

    private void onLocksAreSame() {
        Single.fromCallable(() -> {
            getMvpView().disableBackButton();
            patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
            title.setText(R.string.loginActivity_rememberThat);
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_LONG_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    getDataManager().setLock(firstTryValue);
                    getDataManager().setLockMethod(AppConstants.PATTERN_LOCK_METHOD);
                    getDataManager().setIsFirstLaunch(false);
                    getMvpView().openSecureChooseActivity();
                    getMvpView().finish();
                });
    }

    private void onLocksAreDifferent() {
        Single.fromCallable(() -> {
            patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            title.setText(R.string.patternLock_locksMustBeTheSame);
            getMvpView().startVibration();
            shakeLockView();
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_LONG_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> getMvpView().restartActivity(AppConstants.PATTERN_LOCK_METHOD));

    }

    private void onGoodPattern() {
        patternLockView.setViewMode(
                PatternLockView.PatternViewMode.CORRECT);
        if (AppStatics.IS_IN_RE_ENTERING_LOCK_METHOD_MODE) {
            AppStatics.IS_IN_RE_ENTERING_LOCK_METHOD_MODE = false;
            AppStatics.IS_IN_CHANGE_LOCK_METHOD_MODE = true;
            getMvpView().openTutorialActivity();
        } else {
            AppStatics.WAS_EXIT_NOTIFICATION_BUTTON_CLICK = false;
            getMvpView().openMainActivity();
        }
        getMvpView().finish();
    }

    private void onWrongPattern() {
        Single.fromCallable(() -> {
            patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            getMvpView().startVibration();
            shakeLockView();
            return true;
        })
                .delay(AppConstants.LOGIN_ACTIVITY_SHORT_LOCK_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(i -> {
                    patternLockView.clearPattern();
                    lives--;
                    if (lives == 2) title
                            .setText(R.string.loginActivity_tryAgain);
                    else if (lives == 1) title
                            .setText(R.string.loginActivity_yourLastChance);
                    else if (lives == 0) getMvpView().closeApp();
                });
    }

    private void shakeLockView() {
        patternLockView.startAnimation(AnimationUtils.loadAnimation(
                patternLockView.getContext(),
                R.anim.login_activity_shaking_pattern_view));
    }
}
