package com.kubiakpatryk.safely.data.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

@Entity
public class CipherEntity {

    @Id
    private long id;

    @NameInDb("key_")
    private String key;

    @NameInDb("value_")
    private String value;

    public CipherEntity(long id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public CipherEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CipherEntity{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}