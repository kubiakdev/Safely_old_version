package com.kubiakpatryk.safely.data.db;

import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.CipherEntity_;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity_;
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

    @Override
    public Observable<Long> add(final CipherEntity entity) {
        return Observable.fromCallable(() -> boxStore.boxFor(CipherEntity.class).put(entity));
    }

    @Override
    public Observable<Long> add(final NoteEntity entity) {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class).put(entity));
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
    public Observable<Box<NoteEntity>> getNoteBox() {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class));
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
    public Observable<NoteEntity> getAllNoteEntity() {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class).getAll())
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
    public Observable<NoteEntity> getNoteEntity(NoteEntity entity) {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class).query()
                .equal(NoteEntity_.id, entity.getId())
                .equal(NoteEntity_.content, entity.getContent())
                .equal(NoteEntity_.created, entity.getCreated())
                .equal(NoteEntity_.modified, entity.getModified())
                .equal(NoteEntity_.isBookmarked, entity.isBookmarked())
                .build().findFirst());
    }

    @Override
    public Observable<NoteEntity> getNoteEntityById(final long id) {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class).get(id));
    }

    @Override
    public Observable<NoteEntity> getNoteEntityByContent(final String content) {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class)
                .query().equal(NoteEntity_.isBookmarked, content).build().findFirst());
    }

    @Override
    public Observable<NoteEntity> getNoteEntityByCreated(final String created) {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class)
                .query().equal(NoteEntity_.created, created).build().findFirst());
    }

    @Override
    public Observable<NoteEntity> getNoteEntityByModified(final String modified) {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class)
                .query().equal(NoteEntity_.modified, modified).build().findFirst());
    }

    @Override
    public Observable<NoteEntity> getNoteEntityByIsBookmarked(final boolean isBookmarked) {
        return Observable.fromCallable(() -> boxStore.boxFor(NoteEntity.class)
                .query().equal(NoteEntity_.isBookmarked, isBookmarked).build().findFirst());
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
