package com.kubiakpatryk.safely.ui.login;

import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    private int patternTryCount = 0;
    private String patternFirstTry, patternSecondTry;

    @Inject
    LoginPresenter(DataManager dataManager,
                   SchedulerProviderHelper schedulerProviderHelper,
                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void lookAfterPatternLock(PatternLockView patternLockView, TextView textView) {
        if (!getDataManager().getSavedPatternLock().equals("")) textView.setText("Write your pattern lock");
        RxPatternLockView.patternChanges(patternLockView)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribeOn(getSchedulerProviderHelper().io())
                .subscribe(event -> {
                    if (getDataManager().getSavedPatternLock().equals("")) {
                        if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                            if (patternTryCount == 0) {
                                patternFirstTry = PatternLockUtils.patternToString(patternLockView,
                                        event.getPattern());
                                patternTryCount++;
                                textView.setText("Let's write pattern lock again");
                                patternLockView.clearPattern();
                            } else if (patternTryCount == 1) {
                                patternSecondTry = PatternLockUtils.patternToString(patternLockView,
                                        event.getPattern());
                                if (patternFirstTry.equals(patternSecondTry)) {
                                    getDataManager().setPatternLock(patternSecondTry);
                                    getMvpView().openSecureChooseActivity();
                                    patternTryCount++;
                                } else patternLockView.clearPattern();
                            }
                        }
                    } else {
                        if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                                if (getDataManager().getSavedPatternLock().equals(PatternLockUtils
                                        .patternToString(patternLockView, event.getPattern())))
                                    getMvpView().openMainActivity();
                                else patternLockView.clearPattern();
                        }
                    }
                });
    }
}
