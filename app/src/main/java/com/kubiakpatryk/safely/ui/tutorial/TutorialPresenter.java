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
package com.kubiakpatryk.safely.ui.tutorial;

import android.support.constraint.ConstraintLayout;
import android.widget.LinearLayout;

import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.ScreenUtils;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class TutorialPresenter<V extends TutorialMvpView> extends BasePresenter<V>
        implements TutorialMvpPresenter<V> {

    @Inject
    TutorialPresenter(DataManager dataManager,
                             SchedulerProviderHelper schedulerProviderHelper,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void onAgreeButtonClick() {
        if (!isViewAttached()) return;
        getMvpView().openSecureChooseActivity();
    }

    @Override
    public void setLayoutParameters(List<ConstraintLayout> layouts) {
        getCompositeDisposable().add(Observable.fromIterable(layouts)
                        .subscribeOn(getSchedulerProviderHelper().io())
                        .observeOn(getSchedulerProviderHelper().ui())
                        .subscribe(layout -> layout.setLayoutParams(new LinearLayout
                                .LayoutParams(ScreenUtils.getScreenWidth(),
                                ScreenUtils.getScreenHeight()))));
    }
}
