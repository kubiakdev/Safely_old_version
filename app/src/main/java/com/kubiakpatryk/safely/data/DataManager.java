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
package com.kubiakpatryk.safely.data;

import com.kubiakpatryk.safely.data.db.DbHelper;
import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.ContentEntity;
import com.kubiakpatryk.safely.data.db.entity.PasswordEntity;
import com.kubiakpatryk.safely.data.prefs.PrefsHelper;

import javax.inject.Inject;

import io.objectbox.Box;
import io.reactivex.Observable;

public class DataManager implements DataHelper {

    private final DbHelper dbHelper;
    private final PrefsHelper prefsHelper;

    @Inject
    public DataManager(DbHelper dbHelper, PrefsHelper prefsHelper) {
        this.dbHelper = dbHelper;
        this.prefsHelper = prefsHelper;
    }

    @Override
    public Observable<Long> add(CipherEntity entity) {
        return dbHelper.add(entity);
    }

    @Override
    public Observable<Long> add(ContentEntity entity) {
        return dbHelper.add(entity);
    }

    @Override
    public Observable<Long> add(PasswordEntity entity) {
        return dbHelper.add(entity);
    }

    @Override
    public Observable<Box<CipherEntity>> getCipherBox() {
        return dbHelper.getCipherBox();
    }

    @Override
    public Observable<Box<ContentEntity>> getContentBox() {
        return dbHelper.getContentBox();
    }

    @Override
    public Observable<Box<PasswordEntity>> getPasswordBox() {
        return dbHelper.getPasswordBox();
    }

    @Override
    public Observable<CipherEntity> getAllCipherEntity() {
        return dbHelper.getAllCipherEntity();
    }

    @Override
    public Observable<ContentEntity> getAllContentEntity() {
        return dbHelper.getAllContentEntity();
    }

    @Override
    public Observable<PasswordEntity> getAllPasswordEntity() {
        return dbHelper.getAllPasswordEntity();
    }

    @Override
    public Observable<CipherEntity> getCipherEntityById(long id) {
        return dbHelper.getCipherEntityById(id);
    }

    @Override
    public Observable<CipherEntity> getCipherEntityByKey(String key) {
        return dbHelper.getCipherEntityByKey(key);
    }

    @Override
    public Observable<CipherEntity> getCipherEntityByValue(String value) {
        return dbHelper.getCipherEntityByValue(value);
    }

    @Override
    public Observable<ContentEntity> getContentEntityById(long id) {
        return dbHelper.getContentEntityById(id);
    }

    @Override
    public Observable<ContentEntity> getContentEntityByContent(String content) {
        return dbHelper.getContentEntityByContent(content);
    }

    @Override
    public Observable<ContentEntity> getContentEntityByCreated(String created) {
        return dbHelper.getContentEntityByCreated(created);
    }

    @Override
    public Observable<ContentEntity> getContentEntityByModified(String modified) {
        return dbHelper.getContentEntityByModified(modified);
    }

    @Override
    public Observable<ContentEntity> getContentEntityByFavourite(long favourite) {
        return dbHelper.getContentEntityByFavourite(favourite);
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityById(long id) {
        return dbHelper.getPasswordEntityById(id);
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityByLogin(String login) {
        return dbHelper.getPasswordEntityByLogin(login);
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityByDetail(String detail) {
        return dbHelper.getPasswordEntityByDetail(detail);
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityByFirstPassword(String firstPassword) {
        return dbHelper.getPasswordEntityByFirstPassword(firstPassword);
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityBySecondPassword(String secondPassword) {
        return dbHelper.getPasswordEntityBySecondPassword(secondPassword);
    }

    @Override
    public boolean isFirstLaunch() {
        return prefsHelper.isFirstLaunch();
    }

    @Override
    public void setIsFirstLaunch(boolean value) {
        prefsHelper.setIsFirstLaunch(value);
    }

    @Override
    public boolean isShowingBytes() {
        return prefsHelper.isShowingBytes();
    }

    @Override
    public void setIsShowingBytes(boolean value) {
        prefsHelper.setIsShowingBytes(value);
    }
}
