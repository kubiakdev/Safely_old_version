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
package com.kubiakpatryk.safely.ui.secure_choose;

import com.annimon.stream.Stream;
import com.kubiakpatryk.safely.data.DataManager;
import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.ui.base.BasePresenter;
import com.kubiakpatryk.safely.utils.AppConstants;
import com.kubiakpatryk.safely.utils.rx.SchedulerProviderHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class SecureChoosePresenter<V extends SecureChooseMvpView> extends BasePresenter<V>
        implements SecureChooseMvpPresenter<V> {

    private static List<Integer> values = new ArrayList<>(AppConstants.RANGE);

    @Inject
    SecureChoosePresenter(DataManager dataManager,
                          SchedulerProviderHelper schedulerProviderHelper,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void onGenerateCipherButtonClick() {
        values = new ArrayList<>(AppConstants.RANGE);

        removeCipherIfNotNull();

        initializeValueList();

        getCompositeDisposable().add(Observable.range(0, AppConstants.RANGE)
                .subscribeOn(getSchedulerProviderHelper().io())
                .observeOn(getSchedulerProviderHelper().ui())
                .doOnComplete(() -> {
                    if (!isViewAttached()) return;
                    getMvpView().openMainActivity();
                })
                .subscribe(integer -> {
                    int key = AppConstants.START_RANGE + integer;
                    getDataManager().add(new CipherEntity(key, values.get(integer)))
                            .subscribe();
                    getDataManager().setIsFirstLaunch(false);
                }));
    }

    private void initializeValueList() {
        Stream.iterate(AppConstants.START_RANGE, i -> i + 1)
                .limit(AppConstants.RANGE)
                .forEach(values::add);
        Collections.shuffle(values);
    }

    private void removeCipherIfNotNull() {
        if (!getDataManager().getAllCipherEntity().toList().blockingGet().isEmpty()) {
            getDataManager().getCipherBox()
                    .subscribeOn(getSchedulerProviderHelper().io())
                    .observeOn(getSchedulerProviderHelper().ui())
                    .subscribe(Box::removeAll);

        }
    }


}
