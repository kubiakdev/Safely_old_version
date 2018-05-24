package com.kubiakpatryk.safely.data.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

@Entity
public class NoteEntity {

    @Id(assignable = true)
    private long id;

    @Index
    private String content;

    private String created;
    private String modified;
    private boolean isBookmarked;

    public NoteEntity() {
    }

    public NoteEntity(String content, String created, String modified, boolean isBookmarked) {
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.isBookmarked = isBookmarked;
    }

    public NoteEntity(long id, String content, String created,
                      String modified, boolean isBookmarked) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.isBookmarked = isBookmarked;
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

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", isBookmarked=" + isBookmarked +
                '}';
    }
}
