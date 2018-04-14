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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.kubiakpatryk.safely.di.annotations.ActivityContext;

import javax.inject.Inject;

public class CustomFab extends FloatingActionButton {

    public static boolean IS_FAB_SHOW = false;

    @Inject
    public CustomFab(@ActivityContext Context context) {
        super(context);
    }

    public CustomFab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class CustomBehavior extends FloatingActionButton.Behavior {

        private boolean isFirstTouch = true;
        private int buttonHeight;

        public CustomBehavior() {
        }

        public CustomBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                           @NonNull FloatingActionButton child,
                                           @NonNull View directTargetChild,
                                           @NonNull View target,
                                           int axes,
                                           int type) {
            return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
        }

        @Override
        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                   @NonNull FloatingActionButton child,
                                   @NonNull View target,
                                   int dxConsumed,
                                   int dyConsumed,
                                   int dxUnconsumed,
                                   int dyUnconsumed,
                                   int type) {
            super.onNestedScroll(coordinatorLayout,
                    child,
                    target,
                    dxConsumed,
                    dyConsumed,
                    dxUnconsumed,
                    dyUnconsumed,
                    type);

            if (dyConsumed > 0) {
                CoordinatorLayout.LayoutParams layoutParams =
                        (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                setHeightIfFirstTouch(child, layoutParams);
                startAnimation(child, buttonHeight);
            } else if (dyConsumed < 0) {
                startAnimation(child, -10);
            }
        }

        private void setHeightIfFirstTouch(FloatingActionButton child,
                                           CoordinatorLayout.LayoutParams layoutParams) {
            if (isFirstTouch) {
                buttonHeight = child.getHeight() + layoutParams.bottomMargin;
                isFirstTouch = false;
            }
        }

        private void startAnimation(FloatingActionButton child, int height) {
            child.animate().translationY(height).
                    setInterpolator(new LinearInterpolator()).start();
        }
    }
}
