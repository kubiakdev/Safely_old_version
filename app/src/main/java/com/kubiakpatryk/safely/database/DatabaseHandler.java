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
package com.kubiakpatryk.safely.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kubiakpatryk.safely.dagger2.annotations.ApplicationContext;
import com.kubiakpatryk.safely.database.cipher.CipherTableMethods;
import com.kubiakpatryk.safely.database.content.ContentTableMethods;
import com.kubiakpatryk.safely.database.passwords.PasswordsTableMethods;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "keys";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database = this.getReadableDatabase();

    @Inject
    CipherTableMethods cipherTableMethods;

    @Inject
    ContentTableMethods contentTableMethods;

    @Inject
    PasswordsTableMethods passwordsTableMethods;

    @Inject
    public DatabaseHandler(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        cipherTableMethods.createTable();
//        contentTableMethods.createTable();
//        passwordsTableMethods.createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cipherTableMethods.deleteTableIfExists();
        contentTableMethods.deleteTableIfExists();
        passwordsTableMethods.deleteTableIfExists();

        cipherTableMethods.createTable();
        contentTableMethods.createTable();
        passwordsTableMethods.createTable();
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
