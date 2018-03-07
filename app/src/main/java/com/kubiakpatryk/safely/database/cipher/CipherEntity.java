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
package com.kubiakpatryk.safely.database.cipher;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CipherEntity {

    @Id
    private long id;

    private long key;
    private long value;

    public CipherEntity() {
    }

    public CipherEntity(long key, long value) {
        this.key = key;
        this.value = value;
    }

    public CipherEntity(long id, long key, long value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CipherEntity{" +
                "id=" + id +
                ", key=" + key +
                ", value=" + value +
                '}';
    }
}