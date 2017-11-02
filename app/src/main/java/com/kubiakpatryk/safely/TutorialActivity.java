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
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kubiakpatryk.safely.components.ActivityComponent;
import com.kubiakpatryk.safely.components.DaggerActivityComponent;
import com.kubiakpatryk.safely.modules.ActivityModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialActivity extends AppCompatActivity {

    @BindViews({R.id.tutorialActivity_constraintLayout_leftOuter,
    R.id.tutorialActivity_constraintLayout_leftInner,
    R.id.tutorialActivity_constraintLayout_center,
    R.id.tutorialActivity_constraintLayout_rightInner,
    R.id.tutorialActivity_constraintLayout_rightOuter})
    List<ConstraintLayout> constraintLayoutList;

    @BindViews({R.id.tutorialActivity_radioButton_leftOuter,
    R.id.tutorialActivity_radioButton_leftInner,
    R.id.tutorialActivity_radioButton_center,
    R.id.tutorialActivity_radioButton_rightInner,
    R.id.tutorialActivity_radioButton_rightOuter})
    List<RadioButton> radioButtonList;

    @BindView(R.id.tutorialActivity_radioGroup)
    RadioGroup radioGroup;

    @Inject
    ScreenResolutions screenResolutions;

    @Inject
    CustomGestureDetector customGestureDetector;

    @Inject
    CustomHorizontalScrollView scrollView;

    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent(){
        if(activityComponent == null){
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getApplicationComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        setConstraintLayoutParameters();
    }

    private void setConstraintLayoutParameters(){
        screenResolutions.setLayoutParameters(constraintLayoutList);
    }

    @OnClick(R.id.tutorialActivity_button_agree)
    public void moveToSecureChooseActivity() {
        Intent intent = new Intent(this, SecureChooseActivity.class);
        startActivity(intent);
    }
}
