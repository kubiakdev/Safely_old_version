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
package com.kubiakpatryk.safely.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.kubiakpatryk.safely.di.annotations.ApplicationContext;
import com.kubiakpatryk.safely.utils.AppConstants;

import javax.inject.Inject;

public class PrefsManager implements PrefsHelper {

    private static final String PREFS_FIRST_LAUNCHER_KEY = "PREFS_IS_FIRST_LAUNCH";

    private final SharedPreferences preferences;

    @Inject
    PrefsManager(@ApplicationContext Context context){
        preferences = context.getSharedPreferences(AppConstants.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstLaunch(){
        return preferences.getBoolean(PREFS_FIRST_LAUNCHER_KEY, true);
    }

    public void setIsFirstLaunch(boolean value){
        preferences.edit().putBoolean(PREFS_FIRST_LAUNCHER_KEY, value).apply();
    }

}
