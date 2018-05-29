package com.kubiakpatryk.safely.data;

import com.kubiakpatryk.safely.data.db.DbHelper;
import com.kubiakpatryk.safely.data.db.entity.CipherEntity;
import com.kubiakpatryk.safely.data.db.entity.NoteEntity;
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
    public Observable<Long> add(NoteEntity entity) {
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
    public Observable<Box<NoteEntity>> getNoteBox() {
        return dbHelper.getNoteBox();
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
    public Observable<NoteEntity> getAllNoteEntity() {
        return dbHelper.getAllNoteEntity();
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
    public Observable<NoteEntity> getNoteEntity(NoteEntity entity) {
        return dbHelper.getNoteEntity(entity);
    }

    @Override
    public Observable<NoteEntity> getNoteEntityById(long id) {
        return dbHelper.getNoteEntityById(id);
    }

    @Override
    public Observable<NoteEntity> getNoteEntityByContent(String content) {
        return dbHelper.getNoteEntityByContent(content);
    }

    @Override
    public Observable<NoteEntity> getNoteEntityByCreated(String created) {
        return dbHelper.getNoteEntityByCreated(created);
    }

    @Override
    public Observable<NoteEntity> getNoteEntityByModified(String modified) {
        return dbHelper.getNoteEntityByModified(modified);
    }

    @Override
    public Observable<NoteEntity> getNoteEntityByIsBookmarked(boolean isBookmarked) {
        return dbHelper.getNoteEntityByIsBookmarked(isBookmarked);
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
    public Long getFontSize() {
        return prefsHelper.getFontSize();
    }

    @Override
    public void setFontSize(Long value) {
        prefsHelper.setFontSize(value);
    }

    @Override
    public Long getLastNoteId() {
        return prefsHelper.getLastNoteId();
    }

    @Override
    public void setLastNoteId(long id) {
        prefsHelper.setLastNoteId(id);
    }

    @Override
    public String getPatternLock() {
        return prefsHelper.getPatternLock();
    }

    @Override
    public void setPatternLock(String value) {
        prefsHelper.setPatternLock(value);
    }

    @Override
    public String getRecyclerColor() {
        return prefsHelper.getRecyclerColor();
    }

    @Override
    public void setRecyclerColor(String color) {
        prefsHelper.setRecyclerColor(color);
    }

    @Override
    public String getSortOption() {
        return prefsHelper.getSortOption();
    }

    @Override
    public void setSortOption(String value) {
        prefsHelper.setSortOption(value);
    }
}
