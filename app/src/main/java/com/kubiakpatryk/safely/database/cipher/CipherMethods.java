/*
 * Copyright (C) 2017 Patryk Kubiak
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
package com.kubiakpatryk.safely.database.cipher;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CipherMethods implements CipherMethodHelper {

    final String TABLE_CIPHER = "cipher";

    final String ID = "id";
    final String KEY = "key";
    final String VALUE = "value";

    private SQLiteDatabase database;

    @Inject
    CipherMethods(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void insert(CipherModel model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY, model.getKey());
        contentValues.put(VALUE, model.getValue());
        database.insert(TABLE_CIPHER, null, contentValues);
    }

    @Override
    public CipherModel getModel(String where, String whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                TABLE_CIPHER,
                new String[]{ID,KEY, VALUE},
                where + "=?",
                new String[]{whereArg},
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        assert cursor != null;
        return new CipherModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2));
    }

    @Override
    public List<CipherModel> getAll() {
        List<CipherModel> modelsArray = new ArrayList<>();
        String task = "SELECT * FROM " + TABLE_CIPHER;
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery(task, null);
        if (cursor.moveToFirst()) {
            do {
                CipherModel model = new CipherModel();
                model.setId(cursor.getInt(0));
                model.setKey(cursor.getInt(1));
                model.setValue(cursor.getInt(2));
                modelsArray.add(model);
            } while (cursor.moveToNext());
        }
        return modelsArray;
    }

    @Override
    public void updateRow(CipherModel model, String where, String whereArg) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY, model.getValue());
        contentValues.put(VALUE, model.getValue());
        database.update(TABLE_CIPHER, contentValues,
                where + "=?", new String[]{whereArg});
    }
}
