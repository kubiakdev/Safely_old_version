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

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class ContentEntity {

    @Id
    private long id;

    @Index
    private String content;

    private String created;
    private String modified;
    private long favourite;

    public ContentEntity() {
    }

    public ContentEntity(String content, String created, String modified, long favourite) {
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.favourite = favourite;
    }

    public ContentEntity(long id, String content, String created, String modified, long favourite) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.favourite = favourite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public long getFavourite() {
        return favourite;
    }

    public void setFavourite(long favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return "ContentEntity{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", favourite=" + favourite +
                '}';
    }
}
