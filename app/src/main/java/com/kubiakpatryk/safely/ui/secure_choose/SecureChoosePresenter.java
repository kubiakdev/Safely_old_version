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

    private List<Character> values = new ArrayList<>(AppConstants.RANGE);
    private List<Character> keys = new ArrayList<>(AppConstants.RANGE);

    @Inject
    SecureChoosePresenter(DataManager dataManager,
                          SchedulerProviderHelper schedulerProviderHelper,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProviderHelper, compositeDisposable);
    }

    @Override
    public void onGenerateCipherButtonClick() {
        removeCipherDatabaseIfNotNull();

        initializeLists();

        getCompositeDisposable().add(Observable.range(0, AppConstants.RANGE)
                .observeOn(getSchedulerProviderHelper().ui())
                .subscribeOn(getSchedulerProviderHelper().io())
                .doOnComplete(() -> {
                    if (isViewAttached()) getMvpView().openMainActivity();
                })
                .subscribe(i ->
                        getDataManager().add(new CipherEntity(
                                getCreated(i, keys), getCreated(i, values)))
                                .observeOn(getSchedulerProviderHelper().ui())
                                .subscribeOn(getSchedulerProviderHelper().io())
                                .subscribe()));
    }

    private String getCreated(int integer, List<Character> list) {
        byte[] bytes = list.get(integer).toString().getBytes();
        return (getSeparatedAndMultipliedBytes(bytes) + AppConstants.SPACE_BETWEEN_CHARS);
    }

    private String getSeparatedAndMultipliedBytes(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        long[] array = new long[bytes.length];
        for (int i = 0; i<bytes.length; i++) {
            array[i] = Long.valueOf(String.valueOf(bytes[i])) * AppConstants.MULTIPLIER;
            stringBuilder.append(array[i]).append(AppConstants.SPACE_BETWEEN_BYTES);
        }
        return stringBuilder.toString();
    }

    private void initializeLists() {
        for (char c : AppConstants.ASCII_SIGNS) {
            keys.add(c);
            values.add(c);
        }
        addPolishSigns();
        Collections.shuffle(values);
    }

    private void addPolishSigns() {
        for (char c : AppConstants.POLISH_SIGNS_ARRAY) {
            keys.add(c);
            values.add(c);
        }
    }

    private void removeCipherDatabaseIfNotNull() {
        if (!getDataManager().getAllCipherEntity().toList().blockingGet().isEmpty()) {
            getDataManager().getCipherBox()
                    .subscribeOn(getSchedulerProviderHelper().io())
                    .observeOn(getSchedulerProviderHelper().ui())
                    .subscribe(Box::removeAll);
        }
    }


}
