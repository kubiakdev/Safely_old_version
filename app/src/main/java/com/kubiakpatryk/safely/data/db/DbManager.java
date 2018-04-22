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
package com.kubiakpatryk.safely.data.db;

import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.CipherEntity_;
import com.kubiakpatryk.safely.data.db.entity.ContentEntity;
import com.kubiakpatryk.safely.data.db.entity.ContentEntity_;
import com.kubiakpatryk.safely.data.db.entity.PasswordEntity;
import com.kubiakpatryk.safely.data.db.entity.PasswordEntity_;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Observable;

public class DbManager implements DbHelper {

    private BoxStore boxStore;

    @Inject
    DbManager(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    @Override
    public Observable<Long> add(final CipherEntity entity) {
        return Observable.fromCallable(() -> boxStore.boxFor(CipherEntity.class).put(entity));
    }

    @Override
    public Observable<Long> add(final ContentEntity entity) {
        return Observable.fromCallable(() -> boxStore.boxFor(ContentEntity.class).put(entity));
    }

    @Override
    public Observable<Long> add(final PasswordEntity entity) {
        return Observable.fromCallable(() -> boxStore.boxFor(PasswordEntity.class).put(entity));
    }

    @Override
    public Observable<Box<CipherEntity>> getCipherBox() {
        return Observable.fromCallable(() -> boxStore.boxFor(CipherEntity.class));
    }

    @Override
    public Observable<Box<ContentEntity>> getContentBox() {
        return Observable.fromCallable(() -> boxStore.boxFor(ContentEntity.class));
    }

    @Override
    public Observable<Box<PasswordEntity>> getPasswordBox() {
        return Observable.fromCallable(() -> boxStore.boxFor(PasswordEntity.class));
    }

    @Override
    public Observable<CipherEntity> getAllCipherEntity() {
        return Observable.fromCallable(() -> boxStore.boxFor(CipherEntity.class).getAll())
                .flatMapIterable(cipherEntities -> cipherEntities);
    }

    @Override
    public Observable<ContentEntity> getAllContentEntity() {
        return Observable.fromCallable(() -> boxStore.boxFor(ContentEntity.class).getAll())
                .flatMapIterable(contentEntities -> contentEntities);
    }

    @Override
    public Observable<PasswordEntity> getAllPasswordEntity() {
        return Observable.fromCallable(() -> boxStore.boxFor(PasswordEntity.class).getAll())
                .flatMapIterable(passwordEntities -> passwordEntities);
    }

    @Override
    public Observable<CipherEntity> getCipherEntityById(final long id) {
        return Observable.fromCallable(() -> boxStore.boxFor(CipherEntity.class).get(id));
    }

    @Override
    public Observable<CipherEntity> getCipherEntityByKey(final String key) {
        return Observable.fromCallable(() -> boxStore.boxFor(CipherEntity.class)
                .query().equal(CipherEntity_.key, key).build().findFirst());
    }

    @Override
    public Observable<CipherEntity> getCipherEntityByValue(final String value) {
        return Observable.fromCallable(() -> boxStore.boxFor(CipherEntity.class)
                .query().equal(CipherEntity_.value, value).build().findFirst());
    }

    @Override
    public Observable<ContentEntity> getContentEntityById(final long id) {
        return Observable.fromCallable(() -> boxStore.boxFor(ContentEntity.class).get(id));
    }

    @Override
    public Observable<ContentEntity> getContentEntityByContent(final String content) {
        return Observable.fromCallable(() -> boxStore.boxFor(ContentEntity.class)
                .query().equal(ContentEntity_.content, content).build().findFirst());
    }

    @Override
    public Observable<ContentEntity> getContentEntityByCreated(final String created) {
        return Observable.fromCallable(() -> boxStore.boxFor(ContentEntity.class)
                .query().equal(ContentEntity_.created, created).build().findFirst());
    }

    @Override
    public Observable<ContentEntity> getContentEntityByModified(final String modified) {
        return Observable.fromCallable(() -> boxStore.boxFor(ContentEntity.class)
                .query().equal(ContentEntity_.modified, modified).build().findFirst());
    }

    @Override
    public Observable<ContentEntity> getContentEntityByFavourite(final long favourite) {
        return Observable.fromCallable(() -> boxStore.boxFor(ContentEntity.class)
                .query().equal(ContentEntity_.favourite, favourite).build().findFirst());
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityById(final long id) {
        return Observable.fromCallable(() -> boxStore.boxFor(PasswordEntity.class).get(id));
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityByLogin(String login) {
        return Observable.fromCallable(() -> boxStore.boxFor(PasswordEntity.class)
                .query().equal(PasswordEntity_.login, login).build().findFirst());
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityByDetail(String detail) {
        return Observable.fromCallable(() -> boxStore.boxFor(PasswordEntity.class)
                .query().equal(PasswordEntity_.detail, detail).build().findFirst());
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityByFirstPassword(final String firstPassword) {
        return Observable.fromCallable(() -> boxStore.boxFor(PasswordEntity.class)
                .query().equal(PasswordEntity_.firstPassword, firstPassword).build().findFirst());
    }

    @Override
    public Observable<PasswordEntity> getPasswordEntityBySecondPassword(final String secondPassword) {
        return Observable.fromCallable(() -> boxStore.boxFor(PasswordEntity.class)
                .query().equal(PasswordEntity_.secondPassword, secondPassword).build().findFirst());
    }
}
