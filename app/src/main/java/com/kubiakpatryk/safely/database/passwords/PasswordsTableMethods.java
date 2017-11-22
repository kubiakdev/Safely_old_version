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
package com.kubiakpatryk.safely.database.passwords;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.kubiakpatryk.safely.database.DatabaseTableHelper;

import javax.inject.Inject;

public class PasswordsTableMethods implements DatabaseTableHelper{

    @Inject
    PasswordsMethods passwordsMethods;


    @Inject
    SQLiteDatabase database;

    @Inject
    public PasswordsTableMethods() {
    }

    @Override
    public void createTable() {
        String CREATE_TABLE = "CREATE TABLE " +
                passwordsMethods.TABLE_PASSWORDS + "(" +
                passwordsMethods.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                passwordsMethods.LOGIN + " TEXT NOT NULL," +
                passwordsMethods.DETAIL + " TEXT NOT NULL," +
                passwordsMethods.FIRST_PASSWORD + " TEXT NOT NULL," +
                passwordsMethods.SECOND_PASSWORD + " TEXT NOT NULL" + ")";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void deleteTableIfExists() {
        String DELETE_TABLE = ("DROP TABLE IF EXISTS " + passwordsMethods.TABLE_PASSWORDS);
        database.execSQL(DELETE_TABLE);
    }

    @Override
    public int getRowsSize() {
        return (int) DatabaseUtils.queryNumEntries(database, passwordsMethods.TABLE_PASSWORDS);
    }

    @Override
    public void deleteAllPositions() {
        database.delete(passwordsMethods.TABLE_PASSWORDS, null, null);
    }
}
