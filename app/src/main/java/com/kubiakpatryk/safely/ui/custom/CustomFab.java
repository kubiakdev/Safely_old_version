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
