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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class TutorialActivity extends AppCompatActivity {

    @BindViews({R.id.tutorialActivity_constraintLayout_leftOuter,
    R.id.tutorialActivity_constraintLayout_leftInner,
    R.id.tutorialActivity_constraintLayout_center,
    R.id.tutorialActivity_constraintLayout_rightInner,
    R.id.tutorialActivity_constraintLayout_rightOuter})
    ConstraintLayout[] constraintLayouts;

    @BindViews({R.id.tutorialActivity_radioButton_leftOuter,
    R.id.tutorialActivity_radioButton_leftInner,
    R.id.tutorialActivity_radioButton_center,
    R.id.tutorialActivity_radioButton_rightInner,
    R.id.tutorialActivity_radioButton_rightOuter})
    RadioButton[] radioButtons;

    @BindView(R.id.tutorialActivity_radioGroup)
    RadioGroup radioGroup;


    private ScreenResolutions screenResolutions;
    private TutorialActivityResources activityResources;
    private CustomHorizontalScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        screenResolutions = new ScreenResolutions();
        activityResources = new TutorialActivityResources(getWindow().getDecorView());
        scrollView = new CustomHorizontalScrollView(this, getWindow().getDecorView());


        setConstraintLayoutParameters();
    }

    private void setConstraintLayoutParameters(){
        screenResolutions.setLayoutParameters(activityResources.getConstraintLayout_leftOuter());
        screenResolutions.setLayoutParameters(activityResources.getConstraintLayout_leftInner());
        screenResolutions.setLayoutParameters(activityResources.getConstraintLayout_center());
        screenResolutions.setLayoutParameters(activityResources.getConstraintLayout_rightInner());
        screenResolutions.setLayoutParameters(activityResources.getConstraintLayout_rightOuter());
    }

}
