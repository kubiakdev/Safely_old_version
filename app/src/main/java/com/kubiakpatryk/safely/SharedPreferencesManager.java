/*
 * Copyright (C) 2017 Patryk Kubiak
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
package com.kubiakpatryk.safely;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SharedPreferencesManager{

    private static final String PREFERENCES_NAME = "com.kubiakpatryk.safely";

    private static SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public Object getIfContainsKey(String key) {
        if(sharedPreferences.contains(key))
            return getSuitableObject(key);
        else return null;
    }

    public void setKeyValue(String key, Object value) {
        editor = sharedPreferences.edit();
        setSuitableObject(key,value);
        editor.apply();
    }

    public void clearAllPreferences(){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private Object getSuitableObject(String key){
        Map<String,?> allSharedPreferences = new HashMap<>(sharedPreferences.getAll());
            if (allSharedPreferences.get(key) instanceof Boolean)
                return sharedPreferences.getBoolean(key, false);
            else if (allSharedPreferences.get(key) instanceof Float)
                return sharedPreferences.getFloat(key, 0);
            else if (allSharedPreferences.get(key) instanceof Integer)
                return  sharedPreferences.getInt(key, 0);
            else if (allSharedPreferences.get(key) instanceof Long)
                return  sharedPreferences.getLong(key, 0);
            else if (allSharedPreferences.get(key) instanceof String)
                return sharedPreferences.getString(key, "");
        else {
                throw new NoSuchElementException();
            }
    }

    private void setSuitableObject(String key, Object value){
        if (value instanceof Boolean)
           editor.putBoolean(key,(Boolean) value);
        else if (value instanceof Float)
            editor.putFloat(key, (Float) value);
        else if (value instanceof Integer)
            editor.putInt(key, (Integer) value);
        else if (value instanceof Long)
            editor.putLong(key, (Long) value);
        else if (value instanceof String)
            editor.putString(key, (String) value);
        else {
            throw new NoSuchElementException();
        }
    }
}
