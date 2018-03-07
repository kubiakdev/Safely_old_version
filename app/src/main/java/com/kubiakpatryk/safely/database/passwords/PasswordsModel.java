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

public class PasswordsModel {

    private int id;
    private String login;
    private String detail;
    private String firstPassword;
    private String secondPassword;

    public PasswordsModel() {}

    public PasswordsModel(String login, String detail,
                          String firstPassword, String secondPassword) {
        this.login = login;
        this.detail = detail;
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }

    public PasswordsModel(int id, String login, String detail,
                          String firstPassword, String secondPassword) {
        this.id = id;
        this.login = login;
        this.detail = detail;
        this.firstPassword = firstPassword;
        this.secondPassword = secondPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
