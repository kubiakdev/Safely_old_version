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
package com.kubiakpatryk.safely.main.action_button.small_buttons.model;

import android.content.Intent;

public class SmallActionButtonsModel {

    private int buttonResource;
    private double marginRight;
    private double marginBottom;
    private int animationResource;
    private Intent intent;

    public SmallActionButtonsModel(int buttonResource, double marginRight, double marginBottom,
                                   int animationResource, Intent intent) {
        this.buttonResource = buttonResource;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        this.animationResource = animationResource;
        this.intent = intent;
    }

    public int getButtonResource() {
        return buttonResource;
    }

    public double getMarginRight() {
        return marginRight;
    }

    public double getMarginBottom() {
        return marginBottom;
    }

    public int getAnimationResource() {
        return animationResource;
    }

    public Intent getIntent() {
        return intent;
    }
}
