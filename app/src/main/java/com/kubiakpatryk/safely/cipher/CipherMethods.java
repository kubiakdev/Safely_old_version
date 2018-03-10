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
package com.kubiakpatryk.safely.cipher;

import com.annimon.stream.Stream;
import com.kubiakpatryk.safely.database.BoxManager;
import com.kubiakpatryk.safely.database.CipherEntity;
import com.kubiakpatryk.safely.database.CipherEntity_;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.Property;
import io.objectbox.query.Query;

public class CipherMethods {

    @Inject
    BoxManager boxManager;

    private Box<CipherEntity> entityBox;
    private volatile StringBuilder stringBuilder;
    private Query<CipherEntity> query;

    @Inject
    CipherMethods() {
    }

    public String encrypt(final String source) {
        initializeVariables(source, CipherEntity_.key);
        Stream.iterate(0, i -> i + 1)
                .limit(source.length())
                .forEach(i -> {
                    CipherEntity entity = query.setParameter(CipherEntity_.key,
                            (int) source.charAt(i)).findFirst();
                    stringBuilder.append((char) (entity != null ? entity.getValue() : 0));
                });
        return stringBuilder.toString();
    }

    public String decrypt(final String source) {
        initializeVariables(source, CipherEntity_.value);
        Stream.iterate(0, i -> i + 1)
                .limit(source.length())
                .forEach(i -> {
                    CipherEntity entity = query.setParameter(CipherEntity_.value,
                            (int) source.charAt(i)).findFirst();
                    stringBuilder.append((char) (entity != null ? entity.getKey() : 0));
                });
        return stringBuilder.toString();
    }

    private void initializeVariables(String source, Property property) {
        if (entityBox == null) entityBox = boxManager.getBoxStore().boxFor(CipherEntity.class);
        if (query == null) query = entityBox.query().equal(property, 0).build();
        stringBuilder = new StringBuilder(source.length());
    }
}
