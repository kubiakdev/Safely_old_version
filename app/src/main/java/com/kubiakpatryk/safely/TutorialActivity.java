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

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TutorialActivity extends AppCompatActivity {

    private ScreenResolutions screenResolutions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        screenResolutions = new ScreenResolutions();

        ConstraintLayout constraintLayout_leftOuter =
                (ConstraintLayout) findViewById(R.id.tutorialActivity_constraintLayout_leftOuter);
        ConstraintLayout constraintLayout_leftInner =
                (ConstraintLayout) findViewById(R.id.tutorialActivity_constraintLayout_leftInner);
        ConstraintLayout constraintLayout_center =
                (ConstraintLayout) findViewById(R.id.tutorialActivity_constraintLayout_center);
        ConstraintLayout constraintLayout_rightInner =
                (ConstraintLayout) findViewById(R.id.tutorialActivity_constraintLayout_rightInner);
        ConstraintLayout constraintLayout_rightOuter =
                (ConstraintLayout) findViewById(R.id.tutorialActivity_constraintLayout_rightOuter);
        screenResolutions.setLayoutParameters(constraintLayout_leftOuter);
        screenResolutions.setLayoutParameters(constraintLayout_leftInner);
        screenResolutions.setLayoutParameters(constraintLayout_center);
        screenResolutions.setLayoutParameters(constraintLayout_rightInner);
        screenResolutions.setLayoutParameters(constraintLayout_rightOuter);
    }
}
