package com.kubiakpatryk.safely.ui.splash;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
        implements SplashMvpPresenter<V> {

    @Inject
    SplashPresenter(DataManager dataManager,
                           SchedulerProviderHelper schedulerProviderHelper,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void selectSuitableActivity(){
        if (!isViewAttached()) return;
        if (getDataManager().isFirstLaunch()) getMvpView().openTutorialActivity();
        else getMvpView().openMainActivity();
    }

}
