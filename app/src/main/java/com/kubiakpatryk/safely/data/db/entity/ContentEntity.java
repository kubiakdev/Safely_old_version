package com.kubiakpatryk.safely.data.db.entity;

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
