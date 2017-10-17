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

import static org.junit.Assert.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 26)
public class SharedPreferencesManagerTest {

    private static final String TEST_BOOLEAN_KEY = "TEST_BOOLEAN_KEY";
    private static final String TEST_FLOAT_KEY = "TEST_FLOAT_KEY";
    private static final String TEST_INTEGER_KEY = "TEST_INTEGER_KEY";
    private static final String TEST_LONG_KEY = "TEST_LONG_KEY";
    private static final String TEST_STRING_KEY = "TEST_STRING_KEY";
    private static final boolean TEST_DEFAULT_BOOLEAN_VALUE = false;
    private static final float TEST_DEFAULT_FLOAT_VALUE = 0;
    private static final int TEST_DEFAULT_INTEGER_VALUE = 0;
    private static final long TEST_DEFAULT_LONG_VALUE = 0;
    private static final String TEST_DEFAULT_STRING_VALUE = "";
    private static final boolean EXPECTED_BOOLEAN_VALUE = true;
    private static final float EXPECTED_FLOAT_VALUE = 1;
    private static final int EXPECTED_INTEGER_VALUE = 1;
    private static final long EXPECTED_LONG_VALUE = 1;
    private static final String EXPECTED_STRING_VALUE = "test_passed";

    private LauncherActivity launcherActivity;
    private Context context;
    private SharedPreferencesManager sharedPreferencesManager;
    private SharedPreferences sharedPreferences;

    @Before
    public void initializeObjects() {
        launcherActivity = Robolectric.buildActivity(LauncherActivity.class).create().get();
        context = launcherActivity.getApplicationContext();
        sharedPreferencesManager = new SharedPreferencesManager(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        setDefaultPreferences();
    }

    private void setDefaultPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TEST_BOOLEAN_KEY, TEST_DEFAULT_BOOLEAN_VALUE);
        editor.putFloat(TEST_FLOAT_KEY,TEST_DEFAULT_FLOAT_VALUE);
        editor.putInt(TEST_INTEGER_KEY, TEST_DEFAULT_INTEGER_VALUE);
        editor.putLong(TEST_LONG_KEY,TEST_DEFAULT_LONG_VALUE);
        editor.putString(TEST_STRING_KEY,TEST_DEFAULT_STRING_VALUE);
        editor.apply();
    }

    @Test
    public void testIsLauncherActivityNotNull(){
        Assert.assertNotNull(launcherActivity);
    }

    @Test
    public void testIsContextNotNull(){
        Assert.assertNotNull(context);
    }

    @Test
    public void testIsSharedPreferencesManagerNotNull(){
        Assert.assertNotNull(sharedPreferencesManager);
    }

    @Test
    public void testIsSharedPreferencesNotNull(){
        Assert.assertNotNull(sharedPreferences);
    }

    @Test
    public void testBooleanPreferences(){
        sharedPreferencesManager.setKeyValue(TEST_BOOLEAN_KEY,EXPECTED_BOOLEAN_VALUE);
        boolean result = (Boolean) sharedPreferencesManager.getIfContainsKey(TEST_BOOLEAN_KEY);
        assertTrue(EXPECTED_BOOLEAN_VALUE == result);
    }

    @Test
    public void testFloatPreferences(){
        sharedPreferencesManager.setKeyValue(TEST_FLOAT_KEY,EXPECTED_FLOAT_VALUE);
        float result = (Float) sharedPreferencesManager.getIfContainsKey(TEST_FLOAT_KEY);
        assertTrue(EXPECTED_FLOAT_VALUE == result);
    }

    @Test
    public void testIntegerPreferences(){
        sharedPreferencesManager.setKeyValue(TEST_INTEGER_KEY,EXPECTED_INTEGER_VALUE);
        int result = (Integer) sharedPreferencesManager.getIfContainsKey(TEST_INTEGER_KEY);
        assertTrue(EXPECTED_INTEGER_VALUE == result);
    }

    @Test
    public void testLongPreferences(){
        sharedPreferencesManager.setKeyValue(TEST_LONG_KEY,EXPECTED_LONG_VALUE);
        long result = (Long) sharedPreferencesManager.getIfContainsKey(TEST_LONG_KEY);
        assertTrue(EXPECTED_LONG_VALUE == result);
    }

    @Test
    public void testStringPreferences(){
        sharedPreferencesManager.setKeyValue(TEST_STRING_KEY,EXPECTED_STRING_VALUE);
        String result = (String) sharedPreferencesManager.getIfContainsKey(TEST_STRING_KEY);
        assertTrue(EXPECTED_STRING_VALUE.equals(result));
    }
}