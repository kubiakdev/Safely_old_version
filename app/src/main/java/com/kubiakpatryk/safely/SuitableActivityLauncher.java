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

public class SuitableActivityLauncher {

    public static final String FIRST_LAUNCHER_KEY = "IS_FIRST_LAUNCH";
    public static final String INTENT_EXTRA_CLASS_NAME = "CLASS_NAME";

    private SharedPreferencesManager sharedPreferencesManager;
    private Context context;
    private Intent intent;

    public SuitableActivityLauncher(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        sharedPreferencesManager = new SharedPreferencesManager(context);
    }

    public Intent selectSuitableActivity(){
        if(isFirstLaunch(sharedPreferencesManager)) {
            putExtraToIntent("TutorialActivity");
            return new Intent(context, TutorialActivity.class);
        }
        else {
            putExtraToIntent("MainActivity");
            return new Intent(context, MainActivity.class);
        }
    }

    private void putExtraToIntent(String activityName){
        intent = new Intent().putExtra(INTENT_EXTRA_CLASS_NAME,activityName);
    }

    public boolean isFirstLaunch(SharedPreferencesManager sharedPreferencesManager) {
        if (isFirstLaunchNotExists())
            return true;
        else {
            return (Boolean) sharedPreferencesManager.getIfContainsKey(FIRST_LAUNCHER_KEY);
        }
    }

    private boolean isFirstLaunchNotExists() {
        return sharedPreferencesManager.getIfContainsKey(FIRST_LAUNCHER_KEY) == null;
    }

    public Intent getIntent() {
        return intent;
    }
}
