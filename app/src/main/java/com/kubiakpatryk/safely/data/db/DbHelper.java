package com.kubiakpatryk.safely.data.db;


import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.ContentEntity;
import com.kubiakpatryk.safely.data.db.entity.PasswordEntity;

import io.objectbox.Box;
import io.reactivex.Observable;

public interface DbHelper {

    Observable<Long> add(final CipherEntity entity);

    Observable<Long> add(final ContentEntity entity);

    Observable<Long> add(final PasswordEntity entity);

    Observable<Box<CipherEntity>> getCipherBox();

    Observable<Box<ContentEntity>> getContentBox();

    Observable<Box<PasswordEntity>> getPasswordBox();

    Observable<CipherEntity> getAllCipherEntity();

    Observable<ContentEntity> getAllContentEntity();

    Observable<PasswordEntity> getAllPasswordEntity();

    Observable<CipherEntity> getCipherEntityById(final long id);

    Observable<CipherEntity> getCipherEntityByKey(final String key);

    Observable<CipherEntity> getCipherEntityByValue(final String value);

    Observable<ContentEntity> getContentEntityById(final long id);

    Observable<ContentEntity> getContentEntityByContent(final String content);

    Observable<ContentEntity> getContentEntityByCreated(final String created);

    Observable<ContentEntity> getContentEntityByModified(final String modified);

    Observable<ContentEntity> getContentEntityByFavourite(final long favourite);

    Observable<PasswordEntity> getPasswordEntityById(final long id);

    Observable<PasswordEntity> getPasswordEntityByLogin(final String login);

    Observable<PasswordEntity> getPasswordEntityByDetail(final String detail);

    Observable<PasswordEntity> getPasswordEntityByFirstPassword(final String firstPassword);

    Observable<PasswordEntity> getPasswordEntityBySecondPassword(final String secondPassword);

}
