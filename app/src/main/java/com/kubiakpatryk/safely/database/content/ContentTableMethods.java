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

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.kubiakpatryk.safely.database.DatabaseTableHelper;

import javax.inject.Inject;

public class ContentTableMethods implements DatabaseTableHelper {

    @Inject
    ContentMethods contentMethods;

    @Inject
    SQLiteDatabase database;

    @Inject
    public ContentTableMethods() {
    }

    @Override
    public void createTable() {
        String CREATE_TABLE = "CREATE TABLE " +
                contentMethods.TABLE_CONTENT + "(" +
                contentMethods.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                contentMethods.CONTENT + " TEXT NOT NULL," +
                contentMethods.CREATED + " TEXT NOT NULL," +
                contentMethods.MODIFIED + " TEXT NOT NULL," +
                contentMethods.FAVOURITE + " INTEGER NOT NULL" + ")";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void deleteTableIfExists() {
        String DELETE_TABLE = ("DROP TABLE IF EXISTS " + contentMethods.TABLE_CONTENT);
        database.execSQL(DELETE_TABLE);
    }

    @Override
    public int getRowsSize() {
        return (int) DatabaseUtils.queryNumEntries(database, contentMethods.TABLE_CONTENT);
    }

    @Override
    public void deleteAllPositions() {
        database.delete(contentMethods.TABLE_CONTENT, null, null);
    }
}
