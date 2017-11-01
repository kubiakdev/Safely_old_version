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

import android.content.Intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 26)
public class TutorialActivityTest {

    private TutorialActivity tutorialActivity;

    @Before
    public void setupActivity() {
        tutorialActivity = Robolectric.setupActivity(TutorialActivity.class);
    }

    @Test
    public void startNextActivityOnButtonClick(){
        tutorialActivity.findViewById(R.id.tutorialActivity_button_agree).performClick();
        Intent expectedIntent = new Intent(tutorialActivity, SecureChooseActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(tutorialActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        Assert.assertTrue(actualIntent.filterEquals(expectedIntent));
    }



}