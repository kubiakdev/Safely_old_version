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
package com.kubiakpatryk.safely.database.content;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ContentMethods implements ContentMethodHelper {


    final String TABLE_CONTENT = "content";

    final String ID = "id";
    final String CONTENT = "content";
    final String CREATED = "created";
    final String MODIFIED = "modified";
    final String FAVOURITE = "favourite";

    private SQLiteDatabase database;

    @Inject
    ContentMethods(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void insert(ContentModel model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTENT, model.getContent());
        contentValues.put(CREATED, model.getCreated());
        contentValues.put(MODIFIED, model.getModified());
        contentValues.put(FAVOURITE,model.getFavourite());
        database.insert(TABLE_CONTENT, null, contentValues);
    }

    @Override
    public ContentModel getModel(String where, String whereArg) {
        @SuppressLint("Recycle")
        Cursor cursor = database.query(
                TABLE_CONTENT,
                new String[]{ID, CONTENT, CREATED, MODIFIED, FAVOURITE},
                where + "=?",
                new String[]{whereArg},
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        assert cursor != null;
        return new ContentModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4));
    }

    @Override
    public List<ContentModel> getAll() {
        List<ContentModel> modelsArray = new ArrayList<>();
        String task = "SELECT * FROM " + TABLE_CONTENT;
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery(task, null);
        if (cursor.moveToFirst()) {
            do {
                ContentModel model = new ContentModel();
                model.setId(cursor.getInt(0));
                model.setContent(cursor.getString(1));
                model.setCreated(cursor.getString(2));
                model.setModified(cursor.getString(3));
                model.setFavourite(cursor.getInt(4));
                modelsArray.add(model);
            } while (cursor.moveToNext());
        }
        return modelsArray;
    }

    @Override
    public void updateRow(ContentModel model, String where, String whereArg) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTENT, model.getContent());
        contentValues.put(CREATED, model.getCreated());
        contentValues.put(MODIFIED, model.getModified());
        contentValues.put(FAVOURITE,model.getFavourite());
        database.update(TABLE_CONTENT, contentValues,
                where + "=?", new String[]{whereArg});
    }
}
