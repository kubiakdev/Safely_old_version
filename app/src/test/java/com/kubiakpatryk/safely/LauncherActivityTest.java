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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 26)
public class LauncherActivityTest {

    private LauncherActivity launcherActivity;
    private SharedPreferencesManager sharedPreferencesManager;
    private Object isFirstLaunch;

    @Before
    public void initializeObjects(){
        launcherActivity = Robolectric.buildActivity(LauncherActivity.class).create().get();
        Context context = launcherActivity.getApplicationContext();
        sharedPreferencesManager = new SharedPreferencesManager(context);
        saveCurrentPreferences();
        removePreviousPreferences();
    }

    private void saveCurrentPreferences(){
        if(sharedPreferencesManager.getIfContainsKey(LauncherActivity.FIRST_LAUNCHER_KEY) == null)
            return;
        isFirstLaunch = sharedPreferencesManager.
                getIfContainsKey(LauncherActivity.FIRST_LAUNCHER_KEY);
    }

    private void removePreviousPreferences(){
        sharedPreferencesManager.clearAllPreferences();
    }

    @Test
    public void testIsFirstLaunch_WithNullBoolean(){
        boolean result = launcherActivity.isFirstLaunch(sharedPreferencesManager);
        assertEquals(result,true);
    }

    @Test
    public void testIsFirstLaunch_WithTrueBoolean(){
        sharedPreferencesManager.setKeyValue(LauncherActivity.FIRST_LAUNCHER_KEY,true);
        boolean result = launcherActivity.isFirstLaunch(sharedPreferencesManager);
        assertEquals(result, true);
    }

    @Test
    public void testIsFirstLaunch_WithFalseBoolean() {
        sharedPreferencesManager.setKeyValue(LauncherActivity.FIRST_LAUNCHER_KEY,false);
        boolean result = launcherActivity.isFirstLaunch(sharedPreferencesManager);
        assertEquals(result, false);
    }

    @After
    public void returnOldPreference(){
        if(isFirstLaunch==null)
            return;
        sharedPreferencesManager.setKeyValue(LauncherActivity.FIRST_LAUNCHER_KEY, isFirstLaunch);
    }
}