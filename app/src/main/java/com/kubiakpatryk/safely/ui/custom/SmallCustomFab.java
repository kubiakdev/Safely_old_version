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
package com.kubiakpatryk.safely.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;

import javax.inject.Inject;

public class SmallCustomFab extends FloatingActionButton {

    private double horizontalPosition;
    private double verticalPosition;
    private int animationResourceToShow;
    private int animationResourceToHide;
    private Intent intent;

    @Inject
    public SmallCustomFab(@ActivityContext Context context,
                          double horizontalPosition,
                          double verticalPosition,
                          int animationResourceToShow,
                          int animationResourceToHide,
                          Intent intent) {
        super(context);
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        this.animationResourceToShow = animationResourceToShow;
        this.animationResourceToHide = animationResourceToHide;
        this.intent = intent;
    }

    public SmallCustomFab(Context context) {
        super(context);
    }

    public SmallCustomFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmallCustomFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public double getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(double horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public double getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(double verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public int getAnimationResourceToShow() {
        return animationResourceToShow;
    }

    public void setAnimationResourceToShow(int animationResourceToShow) {
        this.animationResourceToShow = animationResourceToShow;
    }

    public int getAnimationResourceToHide() {
        return animationResourceToHide;
    }

    public void setAnimationResourceToHide(int animationResourceToHide) {
        this.animationResourceToHide = animationResourceToHide;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public static class AnimationScheduler {

        private AnimationScheduler() {
        }

        public static void showFab(final Activity activity, final SmallCustomFab fab) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fab.getLayoutParams();
            params.rightMargin += Math.abs(fab.getWidth() * fab.getHorizontalPosition());
            params.bottomMargin += Math.abs(fab.getHeight() * fab.getVerticalPosition());
            fab.setClickable(true);
            fab.setLayoutParams(params);
            fab.setOnClickListener(view -> activity.startActivity(fab.getIntent()));
            fab.startAnimation(AnimationUtils.loadAnimation(
                    activity, fab.getAnimationResourceToShow()));
        }

        public static void hideFab(final Activity activity, final SmallCustomFab fab) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fab.getLayoutParams();
            params.rightMargin -= Math.abs(fab.getWidth() * fab.getHorizontalPosition());
            params.bottomMargin -= Math.abs(fab.getHeight() * fab.getVerticalPosition());
            fab.setClickable(false);
            fab.setLayoutParams(params);
            fab.setOnClickListener(view -> activity.startActivity(fab.getIntent()));
            fab.startAnimation(AnimationUtils.loadAnimation(
                    activity, fab.getAnimationResourceToHide()));
        }
    }
}
