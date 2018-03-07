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
package com.kubiakpatryk.safely.database.passwords;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PasswordsMethods implements PasswordsMethodHelper {


    final String TABLE_PASSWORDS = "passwords";

    final String ID = "id";
    final String LOGIN = "login";
    final String DETAIL = "detail";
    final String FIRST_PASSWORD = "first_password";
    final String SECOND_PASSWORD = "second_password";

    private SQLiteDatabase database;

    @Inject
    PasswordsMethods(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void insert(PasswordsModel model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN, model.getLogin());
        contentValues.put(DETAIL, model.getDetail());
        contentValues.put(FIRST_PASSWORD, model.getFirstPassword());
        contentValues.put(SECOND_PASSWORD, model.getSecondPassword());
        database.insert(TABLE_PASSWORDS, null, contentValues);
    }

    @Override
    public PasswordsModel getModel(String where, String whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                TABLE_PASSWORDS,
                new String[]{ID, LOGIN, DETAIL, FIRST_PASSWORD, SECOND_PASSWORD},
                where + "=?",
                new String[]{whereArg},
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        assert cursor != null;
        return new PasswordsModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }

    @Override
    public List<PasswordsModel> getAll() {
        List<PasswordsModel> modelsArray = new ArrayList<>();
        String task = "SELECT * FROM " + TABLE_PASSWORDS;
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery(task, null);
        if (cursor.moveToFirst()) {
            do {
                PasswordsModel model = new PasswordsModel();
                model.setId(cursor.getInt(0));
                model.setLogin(cursor.getString(1));
                model.setDetail(cursor.getString(2));
                model.setFirstPassword(cursor.getString(3));
                model.setSecondPassword(cursor.getString(4));
                modelsArray.add(model);
            } while (cursor.moveToNext());
        }
        return modelsArray;
    }

    @Override
    public void updateRow(PasswordsModel model, String where, String whereArg) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOGIN, model.getLogin());
        contentValues.put(DETAIL, model.getDetail());
        contentValues.put(FIRST_PASSWORD, model.getFirstPassword());
        contentValues.put(SECOND_PASSWORD, model.getSecondPassword());
        database.update(TABLE_PASSWORDS, contentValues,
                where + "=?", new String[]{whereArg});
    }
}
