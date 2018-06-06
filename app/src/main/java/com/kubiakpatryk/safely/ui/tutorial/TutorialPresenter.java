package com.kubiakpatryk.safely.ui.tutorial;

import android.support.constraint.ConstraintLayout;
import android.widget.RadioGroup;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.ui.custom.CustomHorizontalScrollView;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.ScreenUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class TutorialPresenter<V extends TutorialMvpView> extends BasePresenter<V>
        implements TutorialMvpPresenter<V>, CustomHorizontalScrollView.OnActionUpEvent {

    @Inject
    TutorialPresenter(DataManager dataManager,
                      SchedulerProviderHelper schedulerProviderHelper,
                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
        CustomHorizontalScrollView.onActionUpEvent = this;
    }

    @Override
    public void initializeRadioGroup(RadioGroup radioGroup) {
        radioGroup.check(getMvpView().getRadioButtonsIdsArray()[0]);
        for (int i = 0; i < getMvpView().getRadioButtonsIdsArray().length; i++)
            radioGroup.getChildAt(i).setOnClickListener(v -> getMvpView().getScrollView()
                    .scrollToView(getRadioButtonInArrayIndex(v.getId())));
    }

    @Override
    public void onPatternLockClick() {
        if (!isViewAttached()) return;
        getMvpView().openLoginActivity(AppConstants.PATTERN_LOCK_METHOD);
    }

    @Override
    public void onPinLockClick() {
        if (!isViewAttached()) return;
        getMvpView().openLoginActivity(AppConstants.PIN_LOCK_METHOD);
    }

    @Override
    public void onPasswordLockClick() {
        if (!isViewAttached()) return;
        getMvpView().openLoginActivity(AppConstants.PASSWORD_LOCK_METHOD);
    }

    @Override
    public void onLackLockClick() {
        if (!isViewAttached()) return;
        getMvpView().openLoginActivity(AppConstants.NO_LOCK_METHOD);
    }

    @Override
    public void setLayoutParameters(List<ConstraintLayout> layouts) {
        getCompositeDisposable().add(Observable.fromIterable(layouts)
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribe(layout -> layout.getLayoutParams().width =
                        ScreenUtils.getScreenWidth()));
    }

    @Override
    public void checkRadioButton(int viewId) {
        getMvpView().getRadioGroup().check(getMvpView().getRadioButtonsIdsArray()[viewId]);
    }

    private int getRadioButtonInArrayIndex(int i) {
        int[] array = getMvpView().getRadioButtonsIdsArray();
        for (int j = 0; j < array.length; j++) {
            if (array[j] == i) return j;
        }
        return -1;
    }
}
