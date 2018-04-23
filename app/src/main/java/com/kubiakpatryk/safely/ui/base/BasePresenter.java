package com.kubiakpatryk.safely.ui.base;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final DataManager dataManager;
    private final SchedulerProviderHelper schedulerProviderHelper;
    private final CompositeDisposable compositeDisposable;

    private V mvpView;

    @Inject
    public BasePresenter(DataManager dataManager,
                         SchedulerProviderHelper schedulerProviderHelper,
                         CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.schedulerProviderHelper = schedulerProviderHelper;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        mvpView = null;
    }

    protected boolean isViewAttached() {
        return mvpView != null;
    }

    protected V getMvpView() {
        return mvpView;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    protected SchedulerProviderHelper getSchedulerProviderHelper() {
        return schedulerProviderHelper;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
