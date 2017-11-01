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
public class SuitableActivityLauncherTest {

    private LauncherActivity launcherActivity;
    private SuitableActivityLauncher suitableActivityLauncher;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private boolean isFirstLaunch;

    @Before
    public void initializeObjects(){
        launcherActivity = Robolectric.buildActivity(LauncherActivity.class).create().get();
        Context context = launcherActivity.getApplicationContext();
        suitableActivityLauncher = new SuitableActivityLauncher
                (launcherActivity.getApplication());
        sharedPreferencesHelper = new SharedPreferencesHelper(
                launcherActivity.getSharedPreferences("default_prefs",Context.MODE_PRIVATE));
        saveCurrentPreferences();
        removePreviousPreferences();
    }

    private void saveCurrentPreferences(){
        if(sharedPreferencesHelper.get
                (SuitableActivityLauncher.FIRST_LAUNCHER_KEY,true) == null)
            return;
        isFirstLaunch = sharedPreferencesHelper.
                get(SuitableActivityLauncher.FIRST_LAUNCHER_KEY,true);
    }

    private void removePreviousPreferences(){
        sharedPreferencesHelper.deleteAllData();
    }

    @Test
    public void testIsFirstLaunch_WithNullBoolean(){
        boolean result = suitableActivityLauncher.isFirstLaunch(sharedPreferencesHelper);
        assertEquals(result,true);
    }

    @Test
    public void testIsFirstLaunch_WithTrueBoolean(){
        sharedPreferencesHelper.put(SuitableActivityLauncher.FIRST_LAUNCHER_KEY,true);
        boolean result = suitableActivityLauncher.isFirstLaunch(sharedPreferencesHelper);
        assertEquals(result, true);
    }

    @Test
    public void testIsFirstLaunch_WithFalseBoolean() {
        sharedPreferencesHelper.put(SuitableActivityLauncher.FIRST_LAUNCHER_KEY,false);
        boolean result = suitableActivityLauncher.isFirstLaunch(sharedPreferencesHelper);
        assertEquals(result, false);
    }

    @After
    public void returnOldPreference(){
        if(isFirstLaunch)
            return;
        sharedPreferencesHelper.put(SuitableActivityLauncher.FIRST_LAUNCHER_KEY, isFirstLaunch);
    }
}