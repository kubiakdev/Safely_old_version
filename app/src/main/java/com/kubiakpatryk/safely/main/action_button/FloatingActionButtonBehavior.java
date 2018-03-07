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
package com.kubiakpatryk.safely.main.action_button;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class FloatingActionButtonBehavior extends FloatingActionButton.Behavior {

    private boolean isFirstTouch = true;
    private int buttonHeight;

    public FloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super();
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
            setHeightIfIsFirstTouch(child, layoutParams);
            startAnimation(child, buttonHeight);
        } else if (dyConsumed < 0) {
            startAnimation(child, -10);
        }
    }

    private void setHeightIfIsFirstTouch(FloatingActionButton child,
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
