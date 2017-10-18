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

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowDisplay;

/**
 * Created by patryk on 18.10.17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 26)
public class ScreenResolutionsTest {

    private LauncherActivity launcherActivity;
    private ScreenResolutions screenResolutions;
    private int testWidth;
    private int testHeight;

    @Before
    public void initializeObjects(){
        launcherActivity = Robolectric.buildActivity(LauncherActivity.class).create().get();
        screenResolutions = new ScreenResolutions();
        setTestResolutions();
    }

    private void setTestResolutions(){
        testWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        testHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Test
    public void testScreenResolutions_compareWidths(){
        assertEquals(testWidth, screenResolutions.getScreenWidth());
    }

    @Test
    public void testScreenResolutions_compareHeights(){
        assertEquals(testHeight, screenResolutions.getScreenHeight());
    }
}