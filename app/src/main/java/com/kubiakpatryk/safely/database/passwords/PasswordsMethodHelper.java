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

import com.kubiakpatryk.safely.database.DatabaseMethodHelper;

import java.util.List;

public interface PasswordsMethodHelper extends DatabaseMethodHelper<PasswordsModel> {

    @Override
    void insert(PasswordsModel model);

    @Override
    PasswordsModel getModel(String where, String whereArg);

    @Override
    List<PasswordsModel> getAll();

    @Override
    void updateRow(PasswordsModel model, String where, String whereArg);
}
