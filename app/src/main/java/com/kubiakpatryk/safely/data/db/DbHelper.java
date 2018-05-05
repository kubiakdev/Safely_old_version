package com.kubiakpatryk.safely.data.db;


import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
import com.kubiakpatryk.safely.data.db.entity.PasswordEntity;

import io.objectbox.Box;
import io.reactivex.Observable;

public interface DbHelper {

    Observable<Long> add(final CipherEntity entity);

    Observable<Long> add(final NoteEntity entity);

    Observable<Long> add(final PasswordEntity entity);

    Observable<Box<CipherEntity>> getCipherBox();

    Observable<Box<NoteEntity>> getNoteBox();

    Observable<Box<PasswordEntity>> getPasswordBox();

    Observable<CipherEntity> getAllCipherEntity();

    Observable<NoteEntity> getAllNoteEntity();

    Observable<PasswordEntity> getAllPasswordEntity();

    Observable<CipherEntity> getCipherEntityById(final long id);

    Observable<CipherEntity> getCipherEntityByKey(final String key);

    Observable<CipherEntity> getCipherEntityByValue(final String value);

    Observable<NoteEntity> getNoteEntity(NoteEntity entity);

    Observable<NoteEntity> getNoteEntityById(final long id);

    Observable<NoteEntity> getNoteEntityByContent(final String content);

    Observable<NoteEntity> getNoteEntityByCreated(final String created);

    Observable<NoteEntity> getNoteEntityByModified(final String modified);

    Observable<NoteEntity> getNoteEntityByFavourite(final long favourite);

    Observable<PasswordEntity> getPasswordEntityById(final long id);

    Observable<PasswordEntity> getPasswordEntityByLogin(final String login);

    Observable<PasswordEntity> getPasswordEntityByDetail(final String detail);

    Observable<PasswordEntity> getPasswordEntityByFirstPassword(final String firstPassword);

    Observable<PasswordEntity> getPasswordEntityBySecondPassword(final String secondPassword);

}
