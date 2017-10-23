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
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TutorialActivityResources {

    private View view;

    public TutorialActivityResources(View view) {
        this.view = view;
    }

    public ConstraintLayout getConstraintLayout_leftOuter() {
        return view.findViewById(R.id.tutorialActivity_constraintLayout_leftOuter);
    }

    public ConstraintLayout getConstraintLayout_leftInner() {
        return view.findViewById(R.id.tutorialActivity_constraintLayout_leftInner);
    }

    public ConstraintLayout getConstraintLayout_center() {
        return (ConstraintLayout) view.findViewById(R.id.tutorialActivity_constraintLayout_center);
    }

    public ConstraintLayout getConstraintLayout_rightInner() {
        return  view.findViewById(R.id.tutorialActivity_constraintLayout_rightInner);
    }

    public ConstraintLayout getConstraintLayout_rightOuter() {
        return view.findViewById(R.id.tutorialActivity_constraintLayout_rightOuter);
    }

    public RadioGroup getRadioGroup(){
        return view.findViewById(R.id.tutorialActivity_radioGroup);
    }

    public RadioButton getRadioButton_leftOuter() {
        return view.findViewById(R.id.tutorialActivity_radioButton_leftOuter);
    }

    public RadioButton getRadioButton_leftInner() {
        return view.findViewById(R.id.tutorialActivity_radioButton_leftInner);
    }

    public RadioButton getRadioButton_center() {
        return view.findViewById(R.id.tutorialActivity_radioButton_center);
    }

    public RadioButton getRadioButton_rightInner() {
        return view.findViewById(R.id.tutorialActivity_radioButton_rightInner);
    }

    public RadioButton getRadioButton_rightOuter() {
        return view.findViewById(R.id.tutorialActivity_radioButton_rightOuter);
    }
}
