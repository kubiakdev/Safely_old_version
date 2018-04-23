package com.kubiakpatryk.safely.data.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class PasswordEntity {

    @Id
    private long id;

    private String login;
    private String detail;
    private String firstPassword;
    private String secondPassword;

    public PasswordEntity() {}

    public PasswordEntity(String login, String detail,
                          String firstPassword, String secondPassword) {
        this.login = login;
        this.detail = detail;
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }

    public PasswordEntity(long id, String login, String detail,
                          String firstPassword, String secondPassword) {
        this.id = id;
        this.login = login;
        this.detail = detail;
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    @Override
    public String toString() {
        return "PasswordEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", detail='" + detail + '\'' +
                ", firstPassword='" + firstPassword + '\'' +
                ", secondPassword='" + secondPassword + '\'' +
                '}';
    }
}
