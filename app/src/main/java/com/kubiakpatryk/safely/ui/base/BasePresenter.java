package com.kubiakpatryk.safely.ui.base;

import android.content.Context;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.di.annotations.ApplicationContext;
import com.kubiakpatryk.safely.utils.CommonUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final DataManager dataManager;
    private final SchedulerProviderHelper schedulerProviderHelper;
    private final CompositeDisposable compositeDisposable;

    private V mvpView;

    @Inject
    @ApplicationContext
    Context context;

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
        setLanguage();
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        mvpView = null;
    }

    @Override
    public void setLanguage() {
        CommonUtils.setLanguage(context, getDataManager().getLanguage());
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
