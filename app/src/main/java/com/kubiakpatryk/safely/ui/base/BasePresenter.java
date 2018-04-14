/*
 * Copyright (C) 2018 Patryk Kubiak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
