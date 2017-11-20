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

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.kubiakpatryk.safely.database.DatabaseTableHelper;
import com.kubiakpatryk.safely.database.cipher.CipherMethods;

import javax.inject.Inject;

public class CipherTableMethods implements DatabaseTableHelper {

    @Inject
    CipherMethods cipherMethods;

    @Inject
    SQLiteDatabase database;

    @Inject
    CipherTableMethods() {
    }

    @Override
    public void createTable() {
        String CREATE_TABLE = "CREATE TABLE " + cipherMethods.TABLE_CIPHER + "("
                + cipherMethods.ID + " INTEGER PRIMARY KEY," + cipherMethods.KEY + " TEXT NOT NULL,"
                + cipherMethods.VALUE + " TEXT NOT NULL" + ")";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void deleteTableIfExists() {
        String DELETE_TABLE = ("DROP TABLE IF EXISTS " + cipherMethods.TABLE_CIPHER);
        database.execSQL(DELETE_TABLE);
    }

    @Override
    public int getRowsSize() {
        return (int) DatabaseUtils.queryNumEntries(database, cipherMethods.TABLE_CIPHER);
    }

    @Override
    public void deleteAllPositions() {
        database.delete(cipherMethods.TABLE_CIPHER, null, null);
    }
}

