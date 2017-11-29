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
import android.content.Intent;

import com.kubiakpatryk.safely.main.MainActivity;

import javax.inject.Inject;

public class SuitableActivityLauncher {

    public static final String FIRST_LAUNCHER_KEY = "IS_FIRST_LAUNCH";

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private Context context;

    @Inject
    public SuitableActivityLauncher(Context context) {
        this.context = context;
    }

    public Intent selectSuitableActivity(){
        if(isFirstLaunch(sharedPreferencesHelper)) {
            return new Intent(context.getApplicationContext(), TutorialActivity.class);
        }
        else {

            return new Intent(context.getApplicationContext(), MainActivity.class);
        }
    }

    public boolean isFirstLaunch(SharedPreferencesHelper sharedPreferencesHelper) {
        if (isFirstLaunchNotExists())
            return true;
        else {
            return sharedPreferencesHelper.get(FIRST_LAUNCHER_KEY,true);
        }
    }

    private boolean isFirstLaunchNotExists() {
        return sharedPreferencesHelper.get(FIRST_LAUNCHER_KEY, true) == null;
    }
}
