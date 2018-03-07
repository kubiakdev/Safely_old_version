/*
 * Copyright (C) 2018 Patryk Kubiak
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
package com.kubiakpatryk.safely.main.action_button.small_buttons;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.kubiakpatryk.safely.main.action_button.small_buttons.model.SmallActionButtonsModel;

import javax.inject.Inject;

class SmallActionButtonsAnimationSchema {

    private Activity activity;
    private FloatingActionButton button;
    private FrameLayout.LayoutParams layoutParams;

    @Inject
    SmallActionButtonsAnimationSchema(Activity activity) {
        this.activity = activity;
    }

    void showSmallButton(SmallActionButtonsModel model) {
        initializeObjects(model);
        layoutParams.rightMargin += button.getWidth() * model.getMarginRight();
        layoutParams.bottomMargin += button.getHeight() * model.getMarginBottom();
        button.setClickable(true);
        prepareButton(model);
    }

    void hideSmallButton(SmallActionButtonsModel model){
        initializeObjects(model);
        layoutParams.rightMargin -= button.getWidth() * model.getMarginRight();
        layoutParams.bottomMargin -= button.getHeight() * model.getMarginBottom();
        button.setClickable(false);
        prepareButton(model);
    }

    private void initializeObjects(SmallActionButtonsModel model){
        button = activity.findViewById(model.getButtonResource());
        layoutParams = (FrameLayout.LayoutParams) button.getLayoutParams();
    }

    private void prepareButton(SmallActionButtonsModel model){
        button.setLayoutParams(layoutParams);
        button.startAnimation(AnimationUtils.loadAnimation(
                activity, model.getAnimationResource()));
    }


}
